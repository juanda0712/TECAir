using AirTECWebAPI.DTOModels.Execution;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ExecutionController : ControllerBase
    {

        private readonly BdAirTecContext _bdAirTecContext;

        public ExecutionController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        [HttpGet("GetExecutionByCriteria")]
        public async Task<ActionResult<ExecutionDTO>> GetExecutionByCriteria(string origin, string destination, DateOnly date)
        {
            // Primero, busca el número de vuelo basado en el origen y destino
            var flight = await _bdAirTecContext.Flights
                .Where(f => f.Origin == origin && f.Destination == destination)
                .FirstOrDefaultAsync();

            if (flight == null)
            {
                // No se encontró un vuelo con el origen y destino proporcionados
                return NotFound("No se encontró un vuelo para la ruta especificada.");
            }

            // Luego, busca la ejecución basada en el número de vuelo y la fecha
            var execution = await _bdAirTecContext.Executions
                .Include(e => e.NumberFlightNavigation)
                .Where(e =>
                    e.NumberFlight == flight.Number &&
                    e.Date == date)
                .FirstOrDefaultAsync();

            if (execution == null)
            {
                return NotFound("No se encontró una ejecución para la ruta y fecha especificada.");
            }

            // Mapea los valores relevantes al DTO y devuélvelo
            var executionDTO = new ExecutionDTO
            {
                Idexecution = execution.Idexecution,
                NumberFlight = execution.NumberFlight,
                Date = execution.Date,
                DepartureTime = execution.DepartureTime,
                Price = execution.Price,
                Status = execution.Status,
                BoardingDoor = execution.BoardingDoor
            };

            return executionDTO;
        }

        [HttpPost("CreateExecution")]
        public async Task<ActionResult<ExecutionDTO>> CreateExecution([FromBody] ExecutionDTO executionDTO)
        {
            if (executionDTO == null)
            {
                return BadRequest("El objeto ExecutionDTO no puede ser nulo.");
            }

            // Puedes mapear los valores del DTO a una nueva ejecución en tu base de datos.
            var newExecution = new Execution
            {
                NumberFlight = executionDTO.NumberFlight,
                Date = executionDTO.Date,
                DepartureTime = executionDTO.DepartureTime,
                Price = executionDTO.Price,
                Status = executionDTO.Status,
                BoardingDoor = executionDTO.BoardingDoor
            };

            _bdAirTecContext.Executions.Add(newExecution);
            await _bdAirTecContext.SaveChangesAsync();

            // Puedes mapear los valores relevantes de la nueva ejecución a un DTO para devolverlos.
            var createdExecutionDTO = new ExecutionDTO
            {
                Idexecution = newExecution.Idexecution,
                NumberFlight = newExecution.NumberFlight,
                Date = newExecution.Date,
                DepartureTime = newExecution.DepartureTime,
                Price = newExecution.Price,
                Status = newExecution.Status,
                BoardingDoor = newExecution.BoardingDoor
            };

            return CreatedAtAction("GetExecutionByCriteria", new { date = newExecution.Date }, createdExecutionDTO);
        }

        [HttpPut("UpdateExecution/{id}")]
        public async Task<ActionResult<ExecutionDTO>> UpdateExecution(int id, ExecutionDTO executionDTO)
        {
            if (id != executionDTO.Idexecution)
            {
                return BadRequest("El ID de ejecución en el cuerpo de la solicitud no coincide con el ID de la URL.");
            }

            var existingExecution = await _bdAirTecContext.Executions.FindAsync(id);
            if (existingExecution == null)
            {
                return NotFound("No se encontró la ejecución con el ID especificado.");
            }

            // Actualiza las propiedades del objeto Execution con los valores del DTO
            existingExecution.NumberFlight = executionDTO.NumberFlight;
            existingExecution.Date = executionDTO.Date;
            existingExecution.DepartureTime = executionDTO.DepartureTime;
            existingExecution.Price = executionDTO.Price;
            existingExecution.Status = executionDTO.Status;
            existingExecution.BoardingDoor = executionDTO.BoardingDoor;

            _bdAirTecContext.Entry(existingExecution).State = EntityState.Modified;

            try
            {
                await _bdAirTecContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ExecutionExists(id))
                {
                    return NotFound("La ejecución con el ID especificado ya no existe.");
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        private bool ExecutionExists(int id)
        {
            return _bdAirTecContext.Executions.Any(e => e.Idexecution == id );
        }

        [HttpDelete("DeleteExecution/{id}")]
        public async Task<ActionResult<ExecutionDTO>> DeleteExecution(int id)
        {
            var execution = await _bdAirTecContext.Executions.FindAsync(id);
            if (execution == null)
            {
                return NotFound("No se encontró la ejecución con el ID especificado.");
            }

            _bdAirTecContext.Executions.Remove(execution);
            await _bdAirTecContext.SaveChangesAsync();

            // Puedes devolver una confirmación o cualquier otra información necesaria
            return Ok("La ejecución con el ID especificado ha sido eliminada.");
        }
    }


}
