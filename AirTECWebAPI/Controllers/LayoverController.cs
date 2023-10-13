using AirTECWebAPI.Models;
using AirTECWebAPI.Models.DTO;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LayoverController : ControllerBase
    {
        private readonly BdAirTecContext _bdAirTecContext;

        public LayoverController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        /// <summary>
        /// Retrieves a list of layovers.
        /// </summary>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<LayoverDTO>>> GetLayovers()
        {
            var layovers = await _bdAirTecContext.Layovers
                .Select(l => new LayoverDTO
                {
                    Name = l.Name,
                    NumberFlight = l.NumberFlight
                })
                .ToListAsync();

            return layovers;
        }

        /// <summary>
        /// Retrieves a specific layover by its unique identifier.
        /// </summary>
        /// <param name="id">The unique identifier of the layover.</param>
        [HttpGet("{id}")]
        public async Task<ActionResult<LayoverDTO>> GetLayover(int id)
        {
            var layover = await _bdAirTecContext.Layovers
                .Where(l => l.NumberFlight == id)
                .Select(l => new LayoverDTO
                {
                    Name = l.Name,
                    NumberFlight = l.NumberFlight
                })
                .FirstOrDefaultAsync();

            if (layover == null)
            {
                return NotFound();
            }

            return layover;
        }

        /// <summary>
        /// Creates a new layover.
        /// </summary>
        /// <param name="layoverDTO">The LayoverDTO containing layover details.</param>
        [HttpPost]
        public async Task<ActionResult<LayoverDTO>> PostLayover(LayoverDTO layoverDTO)
        {
            var layover = new Layover
            {
                Name = layoverDTO.Name,
                NumberFlight = layoverDTO.NumberFlight
            };

            _bdAirTecContext.Layovers.Add(layover);
            await _bdAirTecContext.SaveChangesAsync();

            var createdLayoverDTO = new LayoverDTO
            {
                Name = layover.Name,
                NumberFlight = layover.NumberFlight
            };

            return CreatedAtAction("GetLayover", new { id = layover.NumberFlight }, createdLayoverDTO);
        }

        /// <summary>
        /// Updates an existing layover by its unique identifier.
        /// </summary>
        /// <param name="id">The unique identifier of the layover to update.</param>
        /// <param name="layoverDTO">The LayoverDTO containing updated layover details.</param>
        [HttpPut("{id}")]
        public async Task<IActionResult> PutLayover(int id, LayoverDTO layoverDTO)
        {
            if (id != layoverDTO.NumberFlight)
            {
                return BadRequest();
            }

            var layover = await _bdAirTecContext.Layovers.FindAsync(id);

            if (layover == null)
            {
                return NotFound();
            }

            // Update the properties of the Layover object with values from the DTO
            layover.Name = layoverDTO.Name;

            _bdAirTecContext.Entry(layover).State = EntityState.Modified;

            try
            {
                await _bdAirTecContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!LayoverExists(id))
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
        /// Deletes a specific layover by its unique identifier.
        /// </summary>
        /// <param name="id">The unique identifier of the layover to delete.</param>
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteLayover(int id)
        {
            var layover = await _bdAirTecContext.Layovers.FindAsync(id);

            if (layover == null)
            {
                return NotFound();
            }

            _bdAirTecContext.Layovers.Remove(layover);
            await _bdAirTecContext.SaveChangesAsync();

            return NoContent();
        }

        private bool LayoverExists(int id)
        {
            return _bdAirTecContext.Layovers.Any(e => e.NumberFlight == id);
        }
    }
}
