﻿
using System.ComponentModel.DataAnnotations;
using System;


namespace AirTECWebAPI.DTOModels.Execution
{
    public class ExecutionDTO
    {
        public int? Idexecution { get; set; }
        public int? NumberFlight { get; set; }
        public string PlateNumber { get; set; }
        //YY-MM-DD
        public DateOnly? Date { get; set; }

        //HH:mm:ss
        public TimeOnly? DepartureTime { get; set; }
        public int? Price { get; set; }
        public string? Status { get; set; }
        public int? BoardingDoor { get; set; }

        public ExecutionDTO()
        {
        }

        
    }
}

