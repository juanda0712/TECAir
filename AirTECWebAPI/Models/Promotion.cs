using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Promotion
{
    public int Number { get; set; }

    public int? Idexecution { get; set; }

    public string? Period { get; set; }

    public int? Price { get; set; }

    public virtual Execution? IdexecutionNavigation { get; set; }
}
