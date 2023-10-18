namespace AirTECWebAPI.DTOModels.Flight
{
    public class LayoverDTO
    {
        public int Idlayover { get; set; }
        public int NumberFlight { get; set; }
        public string Origin { get; set; }
        public string Destination { get; set; }

        public LayoverDTO() { }

        public LayoverDTO(int idlayover, int numberflight, string origin , string destination)
        {
            Idlayover = idlayover;
            NumberFlight = numberflight;
            Origin = origin;
            Destination = destination;
        }
    }
}
