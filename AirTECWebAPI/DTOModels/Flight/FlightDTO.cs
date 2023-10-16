namespace AirTECWebAPI.DTOModels.Flight
{
    public class FlightDTO
    {
        public int Number { get; set; }
        public string? Origin { get; set; }
        public string? Destination { get; set; }

        public FlightDTO() { }

        public FlightDTO(int number, string? origin, string? destination)
        {
            Number = number;
            Origin = origin;
            Destination = destination;
        }
    }
}
