using AirTECWebAPI.DTOModels.Reservation;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ReservationController : ControllerBase
    {
        private readonly BdAirTecContext _bdAirTecContext;

        public ReservationController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        // GET: api/Reservation
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ReservationDTO>>> GetReservations()
        {
            var reservations = await _bdAirTecContext.Reservations
                .Select(r => new ReservationDTO
                {
                    Idreservation = r.Idreservation,
                    Idpassenger = r.Idpassenger,
                    Idexecution = r.Idexecution
                })
                .ToListAsync();

            return reservations;
        }

    // GET: api/Reservation/5
    [HttpGet("{id}")]
    public async Task<ActionResult<ReservationDTO>> GetReservation(int id)
    {
        var reservation = await _bdAirTecContext.Reservations
            .Where(r => r.Idreservation == id)
            .Select(r => new ReservationDTO
            {
                Idreservation = r.Idreservation,
                Idpassenger = r.Idpassenger,
                Idexecution = r.Idexecution
            })
            .FirstOrDefaultAsync();

        if (reservation == null)
        {
            return NotFound();
        }

        return reservation;
    }

    // POST: api/Reservation
    [HttpPost]
    public async Task<ActionResult<ReservationDTO>> PostReservation(ReservationDTO reservationDTO)
    {
        var reservation = new Reservation
        {
            Idpassenger = reservationDTO.Idpassenger,
            Idexecution = reservationDTO.Idexecution
        };

        _bdAirTecContext.Reservations.Add(reservation);
        await _bdAirTecContext.SaveChangesAsync();

        reservationDTO.Idreservation = reservation.Idreservation;
        return CreatedAtAction("GetReservation", new { id = reservation.Idreservation }, reservationDTO);
    }

    // PUT: api/Reservation/5
    [HttpPut("{id}")]
    public async Task<IActionResult> PutReservation(int id, ReservationDTO reservationDTO)
    {
        if (id != reservationDTO.Idreservation)
        {
            return BadRequest();
        }

        var reservation = await _bdAirTecContext.Reservations.FindAsync(id);

        if (reservation == null)
        {
            return NotFound();
        }

        reservation.Idpassenger = reservationDTO.Idpassenger;
        reservation.Idexecution = reservationDTO.Idexecution;

        _bdAirTecContext.Entry(reservation).State = EntityState.Modified;

        try
        {
            await _bdAirTecContext.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!ReservationExists(id))
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

    // DELETE: api/Reservation/5
    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteReservation(int id)
    {
        var reservation = await _bdAirTecContext.Reservations.FindAsync(id);

        if (reservation == null)
        {
            return NotFound();
        }

        _bdAirTecContext.Reservations.Remove(reservation);
        await _bdAirTecContext.SaveChangesAsync();
        return NoContent();
    }

    private bool ReservationExists(int id)
    {
        return _bdAirTecContext.Reservations.Any(e => e.Idreservation == id);
    }
}
}
