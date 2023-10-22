using AirTECWebAPI.DTOModels.Student;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class StudentController : ControllerBase
    {
        private readonly BdAirTecContext _bdAirTecContext;

        public StudentController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<StudentDTO>>> GetStudents()
        {
            var students = await _bdAirTecContext.Students
                .Select(s => new StudentDTO
                {
                    UniversityCard = s.UniversityCard,
                    UniversityName = s.UniversityName,
                    Miles = s.Miles,
                    Iduser = s.Iduser
                })
                .ToListAsync();

            return students;
        }

    [HttpGet("{universityCard}")]
    public async Task<ActionResult<StudentDTO>> GetStudentByUniversityCard(int universityCard)
    {
        var student = await _bdAirTecContext.Students
            .Where(s => s.UniversityCard == universityCard)
            .Select(s => new StudentDTO
            {
                UniversityCard = s.UniversityCard,
                UniversityName = s.UniversityName,
                Miles = s.Miles,
                Iduser = s.Iduser
            })
            .FirstOrDefaultAsync();

        if (student == null)
        {
            return NotFound();
        }

        return student;
    }

    [HttpPost]
    public async Task<ActionResult<StudentDTO>> CreateStudent(StudentDTO studentDTO)
    {
        if (studentDTO == null)
        {
            return BadRequest("El objeto StudentDTO no puede ser nulo.");
        }

        var student = new Student
        {
            UniversityCard = studentDTO.UniversityCard,
            UniversityName = studentDTO.UniversityName,
            Miles = studentDTO.Miles,
            Iduser = studentDTO.Iduser
        };

        _bdAirTecContext.Students.Add(student);
        await _bdAirTecContext.SaveChangesAsync();

        studentDTO.UniversityCard = student.UniversityCard;

        return CreatedAtAction("GetStudentByUniversityCard", new { universityCard = student.UniversityCard }, studentDTO);
    }

    [HttpPut("{universityCard}")]
    public async Task<IActionResult> UpdateStudent(int universityCard, StudentDTO studentDTO)
    {
        if (universityCard != studentDTO.UniversityCard)
        {
            return BadRequest("El número de carnet universitario en la URL no coincide con el proporcionado.");
        }

        var student = new Student
        {
            UniversityCard = studentDTO.UniversityCard,
            UniversityName = studentDTO.UniversityName,
            Miles = studentDTO.Miles,
            Iduser = studentDTO.Iduser
        };

        _bdAirTecContext.Entry(student).State = EntityState.Modified;

        try
        {
            await _bdAirTecContext.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!StudentExists(universityCard))
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

    [HttpDelete("{universityCard}")]
    public async Task<IActionResult> DeleteStudent(int universityCard)
    {
        var student = await _bdAirTecContext.Students.FindAsync(universityCard);
        if (student == null)
        {
            return NotFound();
        }

        _bdAirTecContext.Students.Remove(student);
        await _bdAirTecContext.SaveChangesAsync();

        return NoContent();
    }

    private bool StudentExists(int universityCard)
    {
        return _bdAirTecContext.Students.Any(s => s.UniversityCard == universityCard);
    }
}
}
