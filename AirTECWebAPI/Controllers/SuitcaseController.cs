using AirTECWebAPI.DTOModels.Suitcase;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class SuitcaseController : Controller
    {
        private readonly BdAirTecContext _bdAirTecContext;
        public SuitcaseController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }


        // GET: api/Suitcase
        [HttpGet]
        public async Task<ActionResult<IEnumerable<SuitcaseDTO>>> GetSuitcases()
        {
            var suitcases = await _bdAirTecContext.Suitcases
                .Select(s => new SuitcaseDTO
                {
                    Idsuitcase = s.IdsuitCase,
                    Weight = s.Weight,
                    Color = s.Color,
                    Idpassenger = s.Idpassenger
                })
                .ToListAsync();

            return suitcases;
        }

    // GET: api/Suitcase/5
    [HttpGet("{id}")]
    public async Task<ActionResult<SuitcaseDTO>> GetSuitcase(int id)
    {
        var suitcase = await _bdAirTecContext.Suitcases.FindAsync(id);

        if (suitcase == null)
        {
            return NotFound();
        }

        var suitcaseDTO = new SuitcaseDTO
        {
            Idsuitcase = suitcase.IdsuitCase,
            Weight = suitcase.Weight,
            Color = suitcase.Color,
            Idpassenger = suitcase.Idpassenger
        };

        return suitcaseDTO;
    }

   

            /// <summary>
            /// Crea una nueva maleta.
            /// </summary>
            /// <param name="suitcaseDTO">Datos de la maleta a crear.</param>
            /// <returns>Los datos de la maleta creada.</returns>
            [HttpPost]
        public async Task<ActionResult<SuitcaseDTO>> PostSuitcase(SuitcaseDTO suitcaseDTO)
        {
            var suitcase = new Suitcase
            {
                Weight = suitcaseDTO.Weight,
                Color = suitcaseDTO.Color,
                Idpassenger = suitcaseDTO.Idpassenger
            };

            _bdAirTecContext.Suitcases.Add(suitcase);
            await _bdAirTecContext.SaveChangesAsync();


            var createdSuitcaseDTO = new SuitcaseDTO
            { 
                Idsuitcase = suitcase.IdsuitCase,
                Weight = suitcase.Weight,
                Color = suitcase.Color,
                Idpassenger = suitcase.Idpassenger
            };

            return CreatedAtAction("GetSuitcase", new { id = suitcase.IdsuitCase }, createdSuitcaseDTO);
        }

        /// <summary>
        /// Obtiene todas las maletas de un pasajero específico.
        /// </summary>
        /// <param name="idPassenger">ID del pasajero.</param>
        /// <returns>Una lista de maletas del pasajero.</returns>
        [HttpGet("GetSuitcasesByPassenger/{idPassenger}")]
        public async Task<ActionResult<IEnumerable<SuitcaseDTO>>> GetSuitcasesByPassenger(int idPassenger)
        {
            var suitcases = await _bdAirTecContext.Suitcases
                .Where(s => s.Idpassenger == idPassenger)
                .Select(s => new SuitcaseDTO
                {
                    Idsuitcase = s.IdsuitCase,
                    Weight = s.Weight,
                    Color = s.Color,
                    Idpassenger = s.Idpassenger
                })
                .ToListAsync();

            return suitcases;
        }

        // PUT: api/Suitcase/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutSuitcase(int id, SuitcaseDTO suitcaseDTO)
        {
            if (id != suitcaseDTO.Idsuitcase)
            {
                return BadRequest();
            }

            var suitcase = await _bdAirTecContext.Suitcases.FindAsync(id);

            if (suitcase == null)
            {
                return NotFound();
            }

            suitcase.Weight = suitcaseDTO.Weight;
            suitcase.Color = suitcaseDTO.Color;
            suitcase.Idpassenger = suitcaseDTO.Idpassenger;

            _bdAirTecContext.Entry(suitcase).State = EntityState.Modified;

            try
            {
                await _bdAirTecContext.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!SuitcaseExists(id))
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

        // DELETE: api/Suitcase/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteSuitcase(int id)
        {
            var suitcase = await _bdAirTecContext.Suitcases.FindAsync(id);

            if (suitcase == null)
            {
                return NotFound();
            }

            _bdAirTecContext.Suitcases.Remove(suitcase);
            await _bdAirTecContext.SaveChangesAsync();

            return NoContent();
        }

        private bool SuitcaseExists(int id)
        {
            return _bdAirTecContext.Suitcases.Any(s => s.IdsuitCase == id);
        }
    }
}







