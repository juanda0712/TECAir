using AirTECWebAPI.DTOModels.Airport;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AirportController : ControllerBase
    {

        private readonly BdAirTecContext _bdAirTecContext;

        public AirportController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        /// <summary>
        /// Retrieves a list of airports.
        /// </summary>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<AirportDTO>>> GetAirports()
        {
            var airports = await _bdAirTecContext.Airports
                .Select(a => new AirportDTO
                {
                    Name = a.Name,
                    City = a.City
                })
                .ToListAsync();

            return airports;
        }

        /// <summary>
        /// Retrieves a specific airport by its unique identifier.
        /// </summary>
        /// <param name="name">The unique identifier of the airport.</param>
        [HttpGet("{name}")]
        public async Task<ActionResult<AirportDTO>> GetAirport(string name)
        {
            var airport = await _bdAirTecContext.Airports
                .Where(a => a.Name == name)
                .Select(a => new AirportDTO
                {
                    Name = a.Name,
                    City = a.City
                })
                .FirstOrDefaultAsync();

            if (airport == null)
            {
                return NotFound();
            }

            return airport;
        }

        /// <summary>
        /// Creates a new airport.
        /// </summary>
        /// <param name="airportDTO">The AirportDTO containing airport details.</param>
        [HttpPost]
        public async Task<ActionResult<AirportDTO>> PostAirport(AirportDTO airportDTO)
        {
            var airport = new Airport
            {
                Name = airportDTO.Name,
                City = airportDTO.City
            };

            _bdAirTecContext.Airports.Add(airport);
            await _bdAirTecContext.SaveChangesAsync();

            airportDTO.Name = airport.Name;
            airportDTO.City = airport.City;

            return CreatedAtAction("GetAirport", new { name = airport.Name }, airportDTO);
        }

        /// <summary>
        /// Updates an existing airport by its unique identifier.
        /// </summary>
        /// <param name="name">The unique identifier of the airport to update.</param>
        /// <param name="airportDTO">The AirportDTO containing updated airport details.</param>
        [HttpPut("{name}")]
        public async Task<IActionResult> PutAirport(string name, AirportDTO airportDTO)
        {
            if (name != airportDTO.Name)
            {
                return BadRequest();
            }

            var airport = await _bdAirTecContext.Airports.FindAsync(name);

            if (airport == null)
            {
                return NotFound();
            }

            airport.Name = airportDTO.Name;
            airport.City = airportDTO.City;

            _bdAirTecContext.Entry(airport).State = EntityState.Modified;

            try
            {
                await _bdAirTecContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!AirportExists(name))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        /// <summary>
        /// Deletes a specific airport by its unique identifier.
        /// </summary>
        /// <param name="name">The unique identifier of the airport to delete.</param>
        [HttpDelete("{name}")]
        public async Task<IActionResult> DeleteAirport(string name)
        {
            var airport = await _bdAirTecContext.Airports.FindAsync(name);

            if (airport == null)
            {
                return NotFound();
            }

            _bdAirTecContext.Airports.Remove(airport);
            await _bdAirTecContext.SaveChangesAsync();

            return NoContent();
        }

        private bool AirportExists(string name)
        {
            return _bdAirTecContext.Airports.Any(e => e.Name == name);
        }
    }
}
