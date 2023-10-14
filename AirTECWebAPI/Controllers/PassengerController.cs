using AirTECWebAPI.DTOModels.Passenger;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PassengerController : ControllerBase
    {
        private readonly BdAirTecContext _bdAirTecContext;

        public PassengerController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        // GET: api/Passenger
        [HttpGet]
        public async Task<ActionResult<IEnumerable<PassengerDTO>>> GetPassengers()
        {
            var passengers = await _bdAirTecContext.Passengers
                .Select(p => new PassengerDTO
                {
                    Idpassenger = p.Idpassenger,
                    Fullname = p.Fullname,
                    Phonenumber = p.Phonenumber
                })
                .ToListAsync();

            return passengers;
        }

    // GET: api/Passenger/5
    [HttpGet("{id}")]
    public async Task<ActionResult<PassengerDTO>> GetPassenger(int id)
    {
        var passenger = await _bdAirTecContext.Passengers
            .Where(p => p.Idpassenger == id)
            .Select(p => new PassengerDTO
            {
                Idpassenger = p.Idpassenger,
                Fullname = p.Fullname,
                Phonenumber = p.Phonenumber
            })
            .FirstOrDefaultAsync();

        if (passenger == null)
        {
            return NotFound();
        }

        return passenger;
    }

    // POST: api/Passenger
    [HttpPost]
    public async Task<ActionResult<PassengerDTO>> PostPassenger(PassengerDTO passengerDTO)
    {
        var passenger = new Passenger
        {
            Fullname = passengerDTO.Fullname,
            Phonenumber = passengerDTO.Phonenumber
        };

        _bdAirTecContext.Passengers.Add(passenger);
        await _bdAirTecContext.SaveChangesAsync();

        passengerDTO.Idpassenger = passenger.Idpassenger;
        return CreatedAtAction("GetPassenger", new { id = passenger.Idpassenger }, passengerDTO);
    }

    // PUT: api/Passenger/5
    [HttpPut("{id}")]
    public async Task<IActionResult> PutPassenger(int id, PassengerDTO passengerDTO)
    {
        if (id != passengerDTO.Idpassenger)
        {
            return BadRequest();
        }

        var passenger = await _bdAirTecContext.Passengers.FindAsync(id);

        if (passenger == null)
        {
            return NotFound();
        }

        passenger.Fullname = passengerDTO.Fullname;
        passenger.Phonenumber = passengerDTO.Phonenumber;

        _bdAirTecContext.Entry(passenger).State = EntityState.Modified;

        try
        {
            await _bdAirTecContext.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!PassengerExists(id))
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

    // DELETE: api/Passenger/5
    [HttpDelete("{id}")]
    public async Task<IActionResult> DeletePassenger(int id)
    {
        var passenger = await _bdAirTecContext.Passengers.FindAsync(id);

        if (passenger == null)
        {
            return NotFound();
        }

        _bdAirTecContext.Passengers.Remove(passenger);
        await _bdAirTecContext.SaveChangesAsync();
        return NoContent();
    }

    private bool PassengerExists(int id)
    {
        return _bdAirTecContext.Passengers.Any(e => e.Idpassenger == id);
    }
}
}
