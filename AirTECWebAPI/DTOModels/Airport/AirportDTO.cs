namespace AirTECWebAPI.DTOModels.Airport
{
    public class AirportDTO
    {
        public string Name { get; set; } = null!;
        public string? City { get; set; }

        public AirportDTO() { }

        public AirportDTO(string name, string? city)
        {
            Name = name;
            City = city;
        }
    }
}
