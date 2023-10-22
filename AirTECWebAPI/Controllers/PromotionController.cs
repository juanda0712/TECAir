using AirTECWebAPI.DTOModels.Promotion;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using static System.Runtime.InteropServices.JavaScript.JSType;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PromotionController : ControllerBase
    {

        private readonly BdAirTecContext _bdAirTecContext;

        public PromotionController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        /// <summary>
        /// Retrieves a list of promotions.
        /// </summary>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<PromotionDTO>>> GetPromotions()
        {
            var promotions = await _bdAirTecContext.Promotions
                .Select(a => new PromotionDTO
                {
             
                    Number = a.Number,
                    Idexecution = a.Idexecution,
                    Period = a.Period,
                    Price = a.Price

                })
                .ToListAsync();

            return promotions;
        }

        /// <summary>
        /// Retrieves a specific promotion by its unique identifier.
        /// </summary>
        /// <param number="number">The unique identifier of the promotion.</param>
        [HttpGet("{number}")]
        public async Task<ActionResult<PromotionDTO>> GetPromotion(int number)
        {
            var promotion = await _bdAirTecContext.Promotions
                .Where(a => a.Number == number)
                .Select(a => new PromotionDTO
                {
                    Number = a.Number,
                    Idexecution = a.Idexecution,
                    Period = a.Period,
                    Price = a.Price
                })
                .FirstOrDefaultAsync();

            if (promotion == null)
            {
                return NotFound();
            }

            return promotion;
        }

        /// <summary>
        /// Creates a new promotion.
        /// </summary>
        /// <param number="promotionDTO">The PromotionDTO containing promotion details.</param>
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

            _bdAirTecContext.Promotions.Add(promotion);
            await _bdAirTecContext.SaveChangesAsync();

            promotionDTO.Number = promotion.Number;
            promotionDTO.Idexecution = promotion.Idexecution;
            promotionDTO.Period = promotion.Period;
            promotionDTO.Price = promotion.Price;

            return CreatedAtAction("GetPromotion", new { number = promotion.Number }, promotionDTO);
        }

        /// <summary>
        /// Updates an existing promotion by its unique identifier.
        /// </summary>
        /// <param number="number">The unique identifier of the promotion to update.</param>
        /// <param number="promotionDTO">The PromotionDTO containing updated promotion details.</param>
        [HttpPut("{number}")]
        public async Task<IActionResult> PutPromotion(int number, PromotionDTO promotionDTO)
        {
            if (number != promotionDTO.Number)
            {
                return BadRequest();
            }

            var promotion = await _bdAirTecContext.Promotions.FindAsync(number);

            if (promotion == null)
            {
                return NotFound();
            }

            promotion.Number = promotionDTO.Number;
            promotion.Idexecution = promotionDTO.Idexecution;
            promotion.Period = promotionDTO.Period;
            promotion.Price = promotionDTO.Price;


            _bdAirTecContext.Entry(promotion).State = EntityState.Modified;

            try
            {
                await _bdAirTecContext.SaveChangesAsync();
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

        /// <summary>
        /// Deletes a specific promotion by its unique identifier.
        /// </summary>
        /// <param name="number">The unique identifier of the promotion to delete.</param>
        [HttpDelete("{number}")]
        public async Task<IActionResult> DeleteAirport(int number)
        {
            var promotion = await _bdAirTecContext.Promotions.FindAsync(number);

            if (promotion == null)
            {
                return NotFound();
            }

            _bdAirTecContext.Promotions.Remove(promotion);
            await _bdAirTecContext.SaveChangesAsync();

            return NoContent();
        }

        private bool PromotionExists(int number)
        {
            return _bdAirTecContext.Promotions.Any(e => e.Number == number);
        }
    }
}
