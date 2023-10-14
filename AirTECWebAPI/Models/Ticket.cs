using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Ticket
{
    public int Idticket { get; set; }

    public int Taxes { get; set; }

    public int TotalAmount { get; set; }

    public int? SeatNumber { get; set; }

    public int? Idexecution { get; set; }

    public int? Idpassenger { get; set; }

    public int? Iduser { get; set; }

    public virtual Execution? IdexecutionNavigation { get; set; }

    public virtual Passenger? IdpassengerNavigation { get; set; }

    public virtual User? IduserNavigation { get; set; }

    public virtual Seat? Seat { get; set; }
}
