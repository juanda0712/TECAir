using AirTECWebAPI.DTOModels.Execution;
using AirTECWebAPI.DTOModels.Suitcase;
using AirTECWebAPI.DTOModels.Ticket;

using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;


namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class TicketController : Controller
    {
        private readonly BdAirTecContext _bdAirTecContext;
        public TicketController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }


        // GET: api/Ticket
        [HttpGet]
        public async Task<ActionResult<IEnumerable<TicketDTO>>> GetTickets()
        {
            var tickets = await _bdAirTecContext.Tickets
                .Select(s => new TicketDTO
                {
                    Idticket = s.Idticket,
                    Taxes = s.Taxes,
                    TotalAmount = s.TotalAmount,
                    SeatNumber = s.SeatNumber,
                    Idexecution = s.Idexecution,
                    Idpassenger = s.Idpassenger,
                    Iduser = s.Iduser
                })
                .ToListAsync();

            return tickets;
        }

        // GET: api/Ticket/5
        [HttpGet("{id}")]
        public async Task<ActionResult<TicketDTO>> GetTicket(int id)
        {
            var ticket = await _bdAirTecContext.Tickets.FindAsync(id);

            if (ticket == null)
            {
                return NotFound();
            }

            var ticketDTO = new TicketDTO
            {

                Idticket = ticket.Idticket,
                Taxes = ticket.Taxes,
                TotalAmount = ticket.TotalAmount,
                SeatNumber = ticket.SeatNumber,
                Idexecution = ticket.Idexecution,
                Idpassenger = ticket.Idpassenger,
                Iduser = ticket.Iduser
            };

            return ticketDTO;
        }



        /// <summary>
        /// Crea un nuevo tiquete.
        /// </summary>
        /// <param name="ticketDTO">Datos del tiquete a crear.</param>
        /// <returns>Los datos del tiquete creado.</returns>
        
        [HttpPost]
        public async Task<ActionResult<TicketDTO>> PostTicket(TicketDTO ticketDTO)
        {
            var ticket = new Ticket
            {
                Taxes = ticketDTO.Taxes,
                SeatNumber = ticketDTO.SeatNumber,
                Idexecution = ticketDTO.Idexecution,
                Idpassenger = ticketDTO.Idpassenger,
                Iduser = ticketDTO.Iduser
            };

            // Calcular el costo adicional basado en la cantidad de maletas
            var suitcaseCount = await _bdAirTecContext.Suitcases.CountAsync(s => s.Idpassenger == ticketDTO.Idpassenger);
            var execution = await _bdAirTecContext.Executions.FindAsync(ticketDTO.Idexecution);
           
            System.Diagnostics.Debug.WriteLine(suitcaseCount);

            int? additionalCost = execution.Price;

            if (suitcaseCount >= 2)
            {
                additionalCost += 50;
            }
            if (suitcaseCount >= 3)
            {
                additionalCost += 75 * (suitcaseCount-2);
            }


            // Sumar el costo adicional al TotalAmount
            ticket.TotalAmount = (int)additionalCost;
            ticket.Taxes = (int)(ticket.TotalAmount * 0.12);


            _bdAirTecContext.Tickets.Add(ticket);
            await _bdAirTecContext.SaveChangesAsync();

            var createdTicketDTO = new TicketDTO
            {
                Idticket = ticket.Idticket,
                Taxes = ticket.Taxes,
                TotalAmount = ticket.TotalAmount,
                SeatNumber = ticket.SeatNumber,
                Idexecution = ticket.Idexecution,
                Idpassenger = ticket.Idpassenger,
                Iduser = ticket.Iduser
            };

            ticketDTO.Idticket = ticket.Idticket;
            return CreatedAtAction("GetTicket", new { id = ticket.Idticket }, createdTicketDTO);

        }


        
        // PUT: api/Ticket/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutTicket(int id, TicketDTO ticketDTO)
        {
            if (id != ticketDTO.Idticket)
            {
                return BadRequest();
            }

            var ticket = await _bdAirTecContext.Tickets.FindAsync(id);

            if (ticket == null)
            {
                return NotFound();
            }


            ticket.Taxes = ticketDTO.Taxes;
            ticket.TotalAmount = ticketDTO.TotalAmount;
            ticket.SeatNumber = ticketDTO.SeatNumber;
            ticket.Idexecution = ticketDTO.Idexecution;
            ticket.Idpassenger = ticketDTO.Idpassenger;
            ticket.Iduser = ticketDTO.Iduser;

            _bdAirTecContext.Entry(ticket).State = EntityState.Modified;

            try
            {
                await _bdAirTecContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!TicketsExists(id))
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

       
        // DELETE: api/Ticket/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteTicket(int id)
        {
            var ticket = await _bdAirTecContext.Tickets.FindAsync(id);

            if (ticket == null)
            {
                return NotFound();
            }

            _bdAirTecContext.Tickets.Remove(ticket);
            await _bdAirTecContext.SaveChangesAsync();

            return NoContent();
        }

        private bool TicketsExists(int id)
        {
            return _bdAirTecContext.Tickets.Any(s => s.Idticket == id);
        }
        
    }
}



