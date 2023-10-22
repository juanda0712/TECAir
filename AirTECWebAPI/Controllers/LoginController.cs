using AirTECWebAPI.DTOModels.Login;
using AirTECWebAPI.Models;
using AirTECWebAPI.Utils;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoginController : ControllerBase
    {
        private readonly BdAirTecContext _bdAirTecContext;

        public LoginController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        [HttpPost("UserLogin")]
        public async Task<ActionResult> UserLogin([FromBody] LoginRequest loginRequest)
        {
            if (loginRequest == null)
            {
                return BadRequest("La solicitud de inicio de sesión no puede ser nula.");
            }

            var user = await _bdAirTecContext.Users.FirstOrDefaultAsync(u =>
                u.Email == loginRequest.Email &&
                u.Password == loginRequest.Password);

            if (user == null)
            {
                return Unauthorized("Credenciales de inicio de sesión inválidas.");
            }

            return Ok(new { Iduser= user.Iduser });
        }

        [HttpPost("EmployeeLogin")]
        public IActionResult Login([FromBody] LoginRequest loginRequest)
        {
            if (loginRequest == null)
            {
                return BadRequest("La solicitud de inicio de sesión no puede ser nula.");
            }

            // Verifica las credenciales en tu lógica de autenticación hardcodeada
            if (loginRequest.Email == Employees.Username && loginRequest.Password == Employees.Password)
            {
                // Credenciales válidas, puedes generar un token o simplemente devolver un Ok
                return Ok(new { Message = "Inicio de sesión exitoso" });
            }
            else
            {
                return Unauthorized("Credenciales de inicio de sesión inválidas.");
            }
        }
    }
}
