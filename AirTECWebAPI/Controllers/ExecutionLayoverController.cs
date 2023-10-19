using AirTECWebAPI.DTOModels.ExecutionLayover;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ExecutionLayoverController : ControllerBase
    {
        private readonly BdAirTecContext _bdAirTecContext;

        public ExecutionLayoverController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        [HttpGet("GetLayoversByExecution/{idexecution}")]
        public async Task<ActionResult<IEnumerable<ExecutionLayoverDTO>>> GetLayoversByExecution(int idexecution)
        {
            var layovers = await _bdAirTecContext.ExecutionLayovers
                .Where(el => el.Idexecution == idexecution)
                .Select(el => new ExecutionLayoverDTO
                {
                    Idlayover = el.Idlayover,
                    Idexecution = el.Idexecution,
                    Price = el.Price
                })
                .ToListAsync();

            if (layovers == null || !layovers.Any())
            {
                return NotFound("No se encontraron Layovers para la ejecución especificada.");
            }

            return layovers;
        }

        [HttpPost("CreateLayover")]
        public async Task<ActionResult<ExecutionLayoverDTO>> CreateLayover(ExecutionLayoverDTO layoverDTO)
        {
            if (layoverDTO == null)
            {
                return BadRequest("El objeto ExecutionLayoverDTO no puede ser nulo.");
            }

            var newLayover = new ExecutionLayover
            {
                Idlayover = layoverDTO.Idlayover,
                Idexecution = layoverDTO.Idexecution,
                Price = layoverDTO.Price
            };

            _bdAirTecContext.ExecutionLayovers.Add(newLayover);
            await _bdAirTecContext.SaveChangesAsync();

            return CreatedAtAction("GetLayoversByExecution", new { idexecution = newLayover.Idexecution }, layoverDTO);
        }

        [HttpPut("{idlayover}")]
        public async Task<IActionResult> UpdateLayover(int idlayover, ExecutionLayoverDTO layoverDTO)
        {
            if (idlayover != layoverDTO.Idlayover)
            {
                return BadRequest("El ID del Layover no coincide con el ID proporcionado.");
            }

            var existingLayover = await _bdAirTecContext.ExecutionLayovers.FindAsync(idlayover);

            if (existingLayover == null)
            {
                return NotFound("Layover no encontrado.");
            }

            existingLayover.Idexecution = layoverDTO.Idexecution;
            existingLayover.Price = layoverDTO.Price;

            _bdAirTecContext.Entry(existingLayover).State = EntityState.Modified;

            try
            {
                await _bdAirTecContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!LayoverExists(idlayover))
                {
                    return NotFound("Layover no encontrado.");
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        [HttpDelete("{idlayover}")]
        public async Task<IActionResult> DeleteLayover(int idlayover)
        {
            var layover = await _bdAirTecContext.ExecutionLayovers.FindAsync(idlayover);

            if (layover == null)
            {
                return NotFound("Layover no encontrado.");
            }

            _bdAirTecContext.ExecutionLayovers.Remove(layover);
            await _bdAirTecContext.SaveChangesAsync();

            return NoContent();
        }
        private bool LayoverExists(int idlayover)
        {
            return _bdAirTecContext.ExecutionLayovers.Any(e => e.Idlayover == idlayover);
        }

    }
}
