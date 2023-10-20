using AirTECWebAPI.DTOModels.Execution;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;

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


        [HttpGet]
        public async Task<ActionResult<IEnumerable<ExecutionDTO>>> GetExecutions()
        {
            var executions = await _bdAirTecContext.Executions
                .Select(a => new ExecutionDTO
            {
                Idexecution = a.Idexecution,
                NumberFlight = a.NumberFlight,
                PlateNumber = a.PlateNumber,
                Date = a.Date,
                DepartureTime = a.DepartureTime,
                Price = a.Price,
                Status = a.Status,
                BoardingDoor = a.BoardingDoor
            })
                .ToListAsync();

            return executions;
        }

        [HttpGet("GetExecutionByID/{Idexecution}")]
        public async Task<ActionResult<ExecutionDTO>> GetExecutionByID(int Idexecution)
        {
            var execution = await _bdAirTecContext.Executions.FindAsync(Idexecution);

            if (execution == null)
            {
                return NotFound("No se encontró la ejecución con el Idexecution especificado.");
            }

            // Mapea los valores relevantes al DTO y devuélvelos
            var executionDTO = new ExecutionDTO
            {
                Idexecution = execution.Idexecution,
                NumberFlight = execution.NumberFlight,
                PlateNumber = execution.PlateNumber,
                Date = execution.Date,
                DepartureTime = execution.DepartureTime,
                Price = execution.Price,
                Status = execution.Status,
                BoardingDoor = execution.BoardingDoor
            };

            return executionDTO;
        }

        [HttpGet("GetExecutionsByCriteria")]
        public async Task<ActionResult<IEnumerable<ExecutionDTO>>> GetExecutionsByCriteria(string origin, string destination, DateOnly date)
        {
            var flight = await _bdAirTecContext.Flights
                .Where(f => f.Origin == origin && f.Destination == destination)
                .FirstOrDefaultAsync();

            if (flight == null)
            {
                return NotFound("No se encontró un vuelo para la ruta especificada.");
            }

            var executions = await _bdAirTecContext.Executions
                .Include(e => e.NumberFlightNavigation)
                .Where(e =>
                    e.NumberFlight == flight.Number &&
                    e.Date == date)
                .ToListAsync();

            if (!executions.Any())
            {
                return NotFound("No se encontraron ejecuciones para la ruta y fecha especificada.");
            }

            var executionDTOs = executions.Select(execution => new ExecutionDTO
            {
                Idexecution = execution.Idexecution,
                NumberFlight = execution.NumberFlight,
                PlateNumber = execution.PlateNumber,
                Date = execution.Date,
                DepartureTime = execution.DepartureTime,
                Price = execution.Price,
                Status = execution.Status,
                BoardingDoor = execution.BoardingDoor
            });

            return executionDTOs.ToList();
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
                PlateNumber = executionDTO.PlateNumber,
                Date = executionDTO.Date,
                DepartureTime = executionDTO.DepartureTime,
                Price = executionDTO.Price,
                Status = executionDTO.Status,
                BoardingDoor = executionDTO.BoardingDoor
            };

            _bdAirTecContext.Executions.Add(newExecution);
            await _bdAirTecContext.SaveChangesAsync();

            executionDTO.Idexecution = newExecution.Idexecution;

            return CreatedAtAction("GetExecutionByID", new { Idexecution = newExecution.Idexecution }, executionDTO);
        }

        [HttpPut("{id}")]
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
            existingExecution.PlateNumber = executionDTO.PlateNumber;
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

        [HttpDelete("{id}")]
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
