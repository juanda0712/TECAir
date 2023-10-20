using AirTECWebAPI.DTOModels.Seat;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SeatController : ControllerBase
    {
        private readonly BdAirTecContext _bdAirTecContext;

        public SeatController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<SeatDTO>>> GetAllSeats()
        {
            var seats = await _bdAirTecContext.Seats
                .Select(seat => new SeatDTO
                {
                    SeatNumber = seat.SeatNumber,
                    Idexecution = seat.Idexecution
                })
                .ToListAsync();

            return seats;
        }

        [HttpGet("GetSeatsByExecution/{idExecution}")]
        public async Task<ActionResult<IEnumerable<SeatDTO>>> GetSeatsByExecution(int idExecution)
        {
            var seats = await _bdAirTecContext.Seats
                .Where(seat => seat.Idexecution == idExecution)
                .Select(seat => new SeatDTO
                {
                    SeatNumber = seat.SeatNumber,
                    Idexecution = seat.Idexecution
                })
                .ToListAsync();

            if (seats == null || seats.Count == 0)
            {
                return NotFound("No se encontraron asientos para la ejecución especificada.");
            }

            return seats;
        }

        [HttpGet("{seatNumber}/{idExecution}")]
        public async Task<ActionResult<SeatDTO>> GetSeat(int seatNumber, int idExecution)
        {
            var seat = await _bdAirTecContext.Seats
                .Where(seat => seat.SeatNumber == seatNumber && seat.Idexecution == idExecution)
                .Select(seat => new SeatDTO
                {
                    SeatNumber = seat.SeatNumber,
                    Idexecution = seat.Idexecution
                })
                .FirstOrDefaultAsync();

            if (seat == null)
            {
                return NotFound();
            }

            return seat;
        }

        [HttpPost]
        public async Task<ActionResult<SeatDTO>> CreateSeat(SeatDTO seatDTO)
        {
            var seat = new Seat
            {
                SeatNumber = seatDTO.SeatNumber,
                Idexecution = seatDTO.Idexecution
            };

            _bdAirTecContext.Seats.Add(seat);
            await _bdAirTecContext.SaveChangesAsync();

            seatDTO.SeatNumber = seat.SeatNumber;
            seatDTO.Idexecution = seat.Idexecution;

            return CreatedAtAction("GetSeat", new { seatNumber = seat.SeatNumber, idExecution = seat.Idexecution }, seatDTO);
        }

        [HttpPut("{seatNumber}/{idExecution}")]
        public async Task<IActionResult> UpdateSeat(int seatNumber, int idExecution, SeatDTO seatDTO)
        {
            if (seatNumber != seatDTO.SeatNumber || idExecution != seatDTO.Idexecution)
            {
                return BadRequest();
            }

            var existingSeat = await _bdAirTecContext.Seats.FindAsync(seatNumber, idExecution);

            if (existingSeat == null)
            {
                return NotFound();
            }

            existingSeat.SeatNumber = seatDTO.SeatNumber;
            existingSeat.Idexecution = seatDTO.Idexecution;

            _bdAirTecContext.Entry(existingSeat).State = EntityState.Modified;

            try
            {
                await _bdAirTecContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SeatExists(seatNumber, idExecution))
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

    [HttpDelete("{seatNumber}/{idExecution}")]
    public async Task<IActionResult> DeleteSeat(int seatNumber, int idExecution)
    {
        var seat = await _bdAirTecContext.Seats.FindAsync(seatNumber, idExecution);

        if (seat == null)
        {
            return NotFound();
        }

        _bdAirTecContext.Seats.Remove(seat);
        await _bdAirTecContext.SaveChangesAsync();

        return NoContent();
    }

    private bool SeatExists(int seatNumber, int idExecution)
    {
        return _bdAirTecContext.Seats.Any(seat => seat.SeatNumber == seatNumber && seat.Idexecution == idExecution);
    }
}
}
