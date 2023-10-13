using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Layover
{
    public string Name { get; set; } = null!;

    public int? NumberFlight { get; set; }

    public virtual Flight? NumberFlightNavigation { get; set; }
}
