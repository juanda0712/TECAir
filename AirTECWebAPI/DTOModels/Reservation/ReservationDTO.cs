namespace AirTECWebAPI.DTOModels.Reservation
{
    public class ReservationDTO
    {
        public int Idreservation { get; set; }
        public int? Idpassenger { get; set; }
        public int? Idexecution { get; set; }

        public ReservationDTO()
        {
        }
    }
}
