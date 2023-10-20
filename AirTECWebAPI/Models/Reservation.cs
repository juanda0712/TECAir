using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Reservation
{
    public int Idreservation { get; set; }

    public int? Idpassenger { get; set; }

    public int? Idexecution { get; set; }

    public virtual Execution? IdexecutionNavigation { get; set; }

    public virtual User? IduserNavigation { get; set; }
}
