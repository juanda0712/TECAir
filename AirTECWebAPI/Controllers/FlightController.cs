using AirTECWebAPI.DTOModels.Flight;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class FlightController : ControllerBase
    {
        private readonly BdAirTecContext _bdAirTecContext;

        public FlightController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        /// <summary>
        /// Retrieves a list of flights.
        /// </summary>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<FlightDTO>>> GetFlights()
        {
            var flights = await _bdAirTecContext.Flights
                .Select(f => new FlightDTO
                {
                    Number = f.Number,
                    Origin = f.Origin,
                    Destination = f.Destination
                })
                .ToListAsync();

            return flights;
        }

        /// <summary>
        /// Retrieves a specific flight by its unique identifier (Number).
        /// </summary>
        /// <param name="number">The unique identifier (Number) of the flight.</param>
        [HttpGet("{number}")]
        public async Task<ActionResult<FlightDTO>> GetFlight(int number)
        {
            var flight = await _bdAirTecContext.Flights
                .Where(f => f.Number == number)
                .Select(f => new FlightDTO
                {
                    Number = f.Number,
                    Origin = f.Origin,
                    Destination = f.Destination
                })
                .FirstOrDefaultAsync();

            if (flight == null)
            {
                return NotFound();
            }

            return flight;
        }

        /// <summary>
        /// Creates a new flight.
        /// </summary>
        /// <param name="flightDTO">The FlightDTO containing flight details.</param>
        [HttpPost]
        public async Task<ActionResult<FlightDTO>> PostFlight(FlightDTO flightDTO)
        {
            var flight = new Flight
            {
                Origin = flightDTO.Origin,
                Destination = flightDTO.Destination
            };

            _bdAirTecContext.Flights.Add(flight);
            await _bdAirTecContext.SaveChangesAsync();

            var createdFlightDTO = new FlightDTO
            {
                Number = flight.Number,
                Origin = flight.Origin,
                Destination = flight.Destination
            };

            return CreatedAtAction("GetFlight", new { number = flight.Number }, createdFlightDTO);
        }

        /// <summary>
        /// Updates an existing flight by its unique identifier (Number).
        /// </summary>
        /// <param name="number">The unique identifier (Number) of the flight to update.</param>
        /// <param name="flightDTO">The FlightDTO containing updated flight details.</param>
        [HttpPut("{number}")]
        public async Task<IActionResult> PutFlight(int number, FlightDTO flightDTO)
        {
            if (number != flightDTO.Number)
            {
                return BadRequest();
            }

            var flight = await _bdAirTecContext.Flights.FindAsync(number);

            if (flight == null)
            {
                return NotFound();
            }

            // Actualiza las propiedades del objeto Flight con los valores del DTO
            flight.Origin = flightDTO.Origin;
            flight.Destination = flightDTO.Destination;

            _bdAirTecContext.Entry(flight).State = EntityState.Modified;

            try
            {
                await _bdAirTecContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!FlightExists(number))
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
        /// Deletes a specific flight by its unique identifier (Number).
        /// </summary>
        /// <param name="number">The unique identifier (Number) of the flight to delete.</param>
        [HttpDelete("{number}")]
        public async Task<IActionResult> DeleteFlight(int number)
        {
            var flight = await _bdAirTecContext.Flights.FindAsync(number);

            if (flight == null)
            {
                return NotFound();
            }

            _bdAirTecContext.Flights.Remove(flight);
            await _bdAirTecContext.SaveChangesAsync();

            return NoContent();
        }

        private bool FlightExists(int number)
        {
            return _bdAirTecContext.Flights.Any(e => e.Number == number);
        }
    }
}
