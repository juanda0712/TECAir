using System;
using System.Collections.Generic;

namespace AirTECWebAPI.Models;

public partial class Plane
{
    public string PlateNumber { get; set; } = null!;

    public int? ColumnNumber { get; set; }

    public int? RowNumber { get; set; }

    public virtual ICollection<Execution> Executions { get; set; } = new List<Execution>();
}
