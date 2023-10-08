using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Department
{
    public string Dname { get; set; } = null!;

    public int Dnumber { get; set; }

    public virtual ICollection<Employee> Employees { get; set; } = new List<Employee>();
}
