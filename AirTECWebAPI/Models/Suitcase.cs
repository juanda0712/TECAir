using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Suitcase
{
    public int IdsuitCase { get; set; }

    public int Weight { get; set; }

    public string? Color { get; set; }

    public int? Idpassenger { get; set; }

    public virtual Passenger? IdpassengerNavigation { get; set; }
}
