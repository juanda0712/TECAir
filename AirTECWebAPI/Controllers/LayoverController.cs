using AirTECWebAPI.DTOModels.Layover;
using AirTECWebAPI.Models;
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
                .Select(f => new LayoverDTO
                {

                    Idlayover = f.Idlayover,
                    NumberFlight = f.NumberFlight,
                    Origin = f.Origin,
                    Destination = f.Destination

                })
                .ToListAsync();

            return layovers;
        }

        /// <summary>
        /// Retrieves a specific layover by its unique identifier (IDLayover).
        /// </summary>
        /// <param name="idlayover">The unique identifier (IDLayover) of the layover.</param>
        [HttpGet("{idlayover}")]
        public async Task<ActionResult<LayoverDTO>> GetLayover(int idlayover)
        {
            var layover = await _bdAirTecContext.Layovers
                .Where(f => f.Idlayover == idlayover)
                .Select(f => new LayoverDTO
                {
                    Idlayover = f.Idlayover,
                    NumberFlight = f.NumberFlight,
                    Origin = f.Origin,
                    Destination = f.Destination
                   
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
                Idlayover = layoverDTO.Idlayover,
                NumberFlight = layoverDTO.NumberFlight,
                Origin = layoverDTO.Origin,
                Destination = layoverDTO.Destination

            };

            _bdAirTecContext.Layovers.Add(layover);
            await _bdAirTecContext.SaveChangesAsync();

            var createdLayoverDTO = new LayoverDTO
            {
                Idlayover = layover.Idlayover,
                NumberFlight = layover.NumberFlight,
                Origin = layover.Origin,
                Destination = layover.Destination


            };

            return CreatedAtAction("GetLayover", new { idlayover = layover.Idlayover }, createdLayoverDTO);
        }

        /// <summary>
        /// Updates an existing layover by its unique identifier (Idlayover).
        /// </summary>
        /// <param name="idlayover">The unique identifier (Idlayover) of the layover to update.</param>
        /// <param name="layoverDTO">The LayoverDTO containing updated layover details.</param>
        [HttpPut("{idlayover}")]
        public async Task<IActionResult> PutLayover(int idlayover, LayoverDTO layoverDTO)
        {
            if (idlayover != layoverDTO.Idlayover)
            {
                return BadRequest();
            }

            var layover = await _bdAirTecContext.Layovers.FindAsync(idlayover);

            if (layover == null)
            {
                return NotFound();
            }

            // Actualiza las propiedades del objeto Flight con los valores del DTO
            layover.Idlayover = layoverDTO.Idlayover;
            layover.NumberFlight = layoverDTO.NumberFlight;
            layover.Origin = layoverDTO.Origin;
            layover.Destination = layoverDTO.Destination;

            _bdAirTecContext.Entry(layover).State = EntityState.Modified;

            try
            {
                await _bdAirTecContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!LayoversExists(idlayover))
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
        /// Deletes a specific layover by its unique identifier (Idlayover).
        /// </summary>
        /// <param name="idlayover">The unique identifier (Number) of the flight to delete.</param>
        [HttpDelete("{idlayover}")]
        public async Task<IActionResult> DeleteLayover(int idlayover)
        {
            var layover = await _bdAirTecContext.Layovers.FindAsync(idlayover);

            if (layover == null)
            {
                return NotFound();
            }

            _bdAirTecContext.Layovers.Remove(layover);
            await _bdAirTecContext.SaveChangesAsync();

            return NoContent();
        }

        private bool LayoversExists(int idlayover)
        {
            return _bdAirTecContext.Layovers.Any(e => e.Idlayover == idlayover);
        }
    }
}
