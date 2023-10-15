using AirTECWebAPI.DTOModels.Plane;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PlaneController : ControllerBase
    {
        private readonly BdAirTecContext _bdAirTecContext;

        public PlaneController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        /// <summary>
        /// Retrieves a list of planes.
        /// </summary>
        [HttpGet]
        public async Task<ActionResult<IEnumerable<PlaneDTO>>> GetPlanes()
        {
            var planes = await _bdAirTecContext.Planes
                .Select(p => new PlaneDTO
                {
                    PlateNumber = p.PlateNumber,
                    ColumnNumber = p.ColumnNumber,
                    RowNumber = p.RowNumber
                })
                .ToListAsync();

            return planes;
        }

    /// <summary>
    /// Retrieves a specific plane by its plate number.
    /// </summary>
    /// <param name="plateNumber">The plate number of the plane.</param>
    [HttpGet("{plateNumber}")]
    public async Task<ActionResult<PlaneDTO>> GetPlane(string plateNumber)
    {
        var plane = await _bdAirTecContext.Planes
            .Where(p => p.PlateNumber == plateNumber)
            .Select(p => new PlaneDTO
            {
                PlateNumber = p.PlateNumber,
                ColumnNumber = p.ColumnNumber,
                RowNumber = p.RowNumber
            })
            .FirstOrDefaultAsync();

        if (plane == null)
        {
            return NotFound();
        }

        return plane;
    }

    /// <summary>
    /// Creates a new plane.
    /// </summary>
    /// <param name="planeDTO">The PlaneDTO containing plane details.</param>
    [HttpPost]
    public async Task<ActionResult<PlaneDTO>> PostPlane(PlaneDTO planeDTO)
    {
        var plane = new Plane
        {
            PlateNumber = planeDTO.PlateNumber,
            ColumnNumber = planeDTO.ColumnNumber,
            RowNumber = planeDTO.RowNumber
        };

        _bdAirTecContext.Planes.Add(plane);
        await _bdAirTecContext.SaveChangesAsync();

        var createdPlaneDTO = new PlaneDTO
        {
            PlateNumber = plane.PlateNumber,
            ColumnNumber = plane.ColumnNumber,
            RowNumber = plane.RowNumber
        };

        return CreatedAtAction("GetPlane", new { plateNumber = plane.PlateNumber }, createdPlaneDTO);
    }

    /// <summary>
    /// Updates an existing plane by its plate number.
    /// </summary>
    /// <param name="plateNumber">The plate number of the plane to update.</param>
    /// <param name="planeDTO">The PlaneDTO containing updated plane details.</param>
    [HttpPut("{plateNumber}")]
    public async Task<IActionResult> PutPlane(string plateNumber, PlaneDTO planeDTO)
    {
        if (plateNumber != planeDTO.PlateNumber)
        {
            return BadRequest();
        }

        var plane = await _bdAirTecContext.Planes.FindAsync(plateNumber);

        if (plane == null)
        {
            return NotFound();
        }

        // Update the plane properties with values from the DTO
        plane.ColumnNumber = planeDTO.ColumnNumber;
        plane.RowNumber = planeDTO.RowNumber;

        _bdAirTecContext.Entry(plane).State = EntityState.Modified;

        try
        {
            await _bdAirTecContext.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!PlaneExists(plateNumber))
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
    /// Deletes a specific plane by its plate number.
    /// </summary>
    /// <param name="plateNumber">The plate number of the plane to delete.</param>
    [HttpDelete("{plateNumber}")]
    public async Task<IActionResult> DeletePlane(string plateNumber)
    {
        var plane = await _bdAirTecContext.Planes.FindAsync(plateNumber);

        if (plane == null)
        {
            return NotFound();
        }

        _bdAirTecContext.Planes.Remove(plane);
        await _bdAirTecContext.SaveChangesAsync();
        return NoContent();
    }

    private bool PlaneExists(string plateNumber)
    {
        return _bdAirTecContext.Planes.Any(e => e.PlateNumber == plateNumber);
    }
}
}
