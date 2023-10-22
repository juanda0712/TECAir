using System;
using System.Collections.Generic;

namespace AirTECWebAPI.DTOModels.Promotion
{
    public class PromotionDTO
    {
        public int Number { get; set; }
        public int? Idexecution { get; set; }
        public string? Period { get; set; }
        public int? Price { get; set; }
    }
}
