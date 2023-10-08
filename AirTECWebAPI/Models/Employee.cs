using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Employee
{
    public string Fname { get; set; } = null!;

    public string Ssn { get; set; } = null!;

    public int Dno { get; set; }

    public virtual Department DnoNavigation { get; set; } = null!;
}
