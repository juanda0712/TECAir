using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Layover
{
    public int Idlayover { get; set; }

    public int NumberFlight { get; set; }

    public string Origin { get; set; } = null!;

    public string Destination { get; set; } = null!;

    public virtual Airport DestinationNavigation { get; set; } = null!;

    public virtual Flight NumberFlightNavigation { get; set; } = null!;

    public virtual Airport OriginNavigation { get; set; } = null!;
}
