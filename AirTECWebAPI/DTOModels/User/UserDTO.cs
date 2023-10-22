namespace AirTECWebAPI.DTOModels.User
{
    public class UserDTO
    {
        public int Iduser { get; set; }
        public string Fullname { get; set; }
        public int? Phonenumber { get; set; }
        public string Password { get; set; }
        public string Email { get; set; }
        public int? Idpassenger { get; set; }
    }
}
