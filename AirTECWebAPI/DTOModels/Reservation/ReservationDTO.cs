namespace AirTECWebAPI.DTOModels.Reservation
{
    public class ReservationDTO
    {
        public int Idreservation { get; set; }
        public int? Iduser { get; set; }
        public int? Idexecution { get; set; }

        public ReservationDTO()
        {
        }
    }
}
