using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Flight
{
    public int Number { get; set; }

    public string? Origin { get; set; }

    public string? Destination { get; set; }

    public virtual ICollection<Execution> Executions { get; set; } = new List<Execution>();

    public virtual ICollection<Layover> Layovers { get; set; } = new List<Layover>();
}
