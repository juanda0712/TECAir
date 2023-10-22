using AirTECWebAPI.DTOModels.Promotion;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PromotionController : ControllerBase
    {

        private readonly BdAirTecContext _context;

        public PromotionController(BdAirTecContext context)
        {
            _context = context;
        }

        // GET: api/Promotion
        [HttpGet]
        public async Task<ActionResult<IEnumerable<PromotionDTO>>> GetPromotions()
        {
            var promotions = await _context.Promotions
                .Select(p => new PromotionDTO
                {
                    Number = p.Number,
                    Idexecution = p.Idexecution,
                    Period = p.Period,
                    Price = p.Price
                })
                .ToListAsync();

            return promotions;
        }

        // GET: api/Promotion/5
        [HttpGet("{number}")]
        public async Task<ActionResult<PromotionDTO>> GetPromotion(int number)
        {
            var promotion = await _context.Promotions.FindAsync(number);

            if (promotion == null)
            {
                return NotFound();
            }

            var promotionDTO = new PromotionDTO
            {
                Number = promotion.Number,
                Idexecution = promotion.Idexecution,
                Period = promotion.Period,
                Price = promotion.Price
            };

            return promotionDTO;
        }

        // POST: api/Promotion
        [HttpPost]
        public async Task<ActionResult<PromotionDTO>> PostPromotion(PromotionDTO promotionDTO)
        {
            var promotion = new Promotion
            {
                Number = promotionDTO.Number,
                Idexecution = promotionDTO.Idexecution,
                Period = promotionDTO.Period,
                Price = promotionDTO.Price
            };

            _context.Promotions.Add(promotion);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetPromotion", new { number = promotion.Number }, promotionDTO);
        }

        // PUT: api/Promotion/5
        [HttpPut("{number}")]
        public async Task<IActionResult> PutPromotion(int number, PromotionDTO promotionDTO)
        {
            if (number != promotionDTO.Number)
            {
                return BadRequest();
            }

            var promotion = new Promotion
            {
                Number = promotionDTO.Number,
                Idexecution = promotionDTO.Idexecution,
                Period = promotionDTO.Period,
                Price = promotionDTO.Price
            };

            _context.Entry(promotion).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!PromotionExists(number))
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

        // DELETE: api/Promotion/5
        [HttpDelete("{number}")]
        public async Task<IActionResult> DeletePromotion(int number)
        {
            var promotion = await _context.Promotions.FindAsync(number);
            if (promotion == null)
            {
                return NotFound();
            }

            _context.Promotions.Remove(promotion);
            await _context.SaveChangesAsync();
            return NoContent();
        }

        private bool PromotionExists(int number)
        {
            return _context.Promotions.Any(e => e.Number == number);
        }
    }
}
