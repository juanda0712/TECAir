using AirTECWebAPI.Models;
using System.Drawing;
using static System.Runtime.InteropServices.JavaScript.JSType;
using AirTECWebAPI.Models;
using System.Drawing;

namespace AirTECWebAPI.DTOModels.Suitcase
{
    public class TicketDTO
    {
        public int Idticket { get; set; }

        public int Taxes { get; set; }

        public int TotalAmount { get; set; }

        public int? SeatNumber { get; set; }

        public int? Idexecution { get; set; }

        public int? Idpassenger { get; set; }

        public int? Iduser { get; set; }

        public TicketDTO()
        {
        }

        public TicketDTO(int idticket, int taxes, int totalamount, int? seatnumber, int? idexecution, int? idpassenger, int? iduser)
        {
            Idticket = idticket;
            Taxes = taxes;
            TotalAmount = totalamount;
            SeatNumber = seatnumber;
            Idexecution = idexecution;
            Idpassenger = idpassenger;
            Iduser = iduser;

        }
    }
}