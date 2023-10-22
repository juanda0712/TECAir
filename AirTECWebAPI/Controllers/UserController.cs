using AirTECWebAPI.DTOModels.User;
using AirTECWebAPI.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly BdAirTecContext _bdAirTecContext;

        public UserController(BdAirTecContext bdAirTecContext)
        {
            _bdAirTecContext = bdAirTecContext;
        }

        [HttpGet]
        public async Task<ActionResult<IEnumerable<UserDTO>>> GetAllUsers()
        {
            var users = await _bdAirTecContext.Users
                .Select(u => new UserDTO
                {
                    Iduser = u.Iduser,
                    Fullname = u.Fullname,
                    Phonenumber = u.Phonenumber,
                    Password = u.Password,
                    Email = u.Email,
                    Idpassenger = u.Idpassenger
                })
                .ToListAsync();

            return users;
        }

    [HttpGet("{id}")]
    public async Task<ActionResult<UserDTO>> GetUserById(int id)
    {
        var user = await _bdAirTecContext.Users
            .Where(u => u.Iduser == id)
            .Select(u => new UserDTO
            {
                Iduser = u.Iduser,
                Fullname = u.Fullname,
                Phonenumber = u.Phonenumber,
                Password = u.Password,
                Email = u.Email,
                Idpassenger = u.Idpassenger
            })
            .FirstOrDefaultAsync();

        if (user == null)
        {
            return NotFound();
        }

        return user;
    }

    [HttpPost]
    public async Task<ActionResult<UserDTO>> CreateUser(UserDTO userDTO)
    {
        if (userDTO == null)
        {
            return BadRequest("El objeto UserDTO no puede ser nulo.");
        }

        var user = new User
        {
            Fullname = userDTO.Fullname,
            Phonenumber = userDTO.Phonenumber,
            Password = userDTO.Password,
            Email = userDTO.Email,
            Idpassenger = userDTO.Idpassenger
        };

            _bdAirTecContext.Users.Add(user);
        await _bdAirTecContext.SaveChangesAsync();

        userDTO.Iduser = user.Iduser;

        return CreatedAtAction("GetUserById", new { id = user.Iduser }, userDTO);
    }

    [HttpPut("{id}")]
    public async Task<IActionResult> UpdateUser(int id, UserDTO userDTO)
    {
        if (id != userDTO.Iduser)
        {
            return BadRequest("El ID en la URL no coincide con el ID del usuario proporcionado.");
        }

        var user = new User
        {
            Iduser = userDTO.Iduser,
            Fullname = userDTO.Fullname,
            Phonenumber = userDTO.Phonenumber,
            Password = userDTO.Password,
            Email = userDTO.Email,
            Idpassenger = userDTO.Idpassenger
        };

        _bdAirTecContext.Entry(user).State = EntityState.Modified;

        try
        {
            await _bdAirTecContext.SaveChangesAsync();
        }
        catch (DbUpdateConcurrencyException)
        {
            if (!UserExists(id))
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

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteUser(int id)
    {
        var user = await _bdAirTecContext.Users.FindAsync(id);
        if (user == null)
        {
            return NotFound();
        }

            _bdAirTecContext.Users.Remove(user);
        await _bdAirTecContext.SaveChangesAsync();

        return NoContent();
    }

    private bool UserExists(int id)
    {
        return _bdAirTecContext.Users.Any(u => u.Iduser == id);
    }
}
}
