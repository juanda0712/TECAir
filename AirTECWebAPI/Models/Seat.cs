using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Seat
{
    public int SeatNumber { get; set; }

    public int Idexecution { get; set; }

    public virtual Execution IdexecutionNavigation { get; set; } = null!;

    public virtual ICollection<Ticket> Tickets { get; set; } = new List<Ticket>();
}
