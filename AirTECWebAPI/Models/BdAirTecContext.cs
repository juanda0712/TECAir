using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Models;

public partial class BdAirTecContext : DbContext
{
    public BdAirTecContext()
    {
    }

    public BdAirTecContext(DbContextOptions<BdAirTecContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Airport> Airports { get; set; }

    public virtual DbSet<Execution> Executions { get; set; }

    public virtual DbSet<ExecutionLayover> ExecutionLayovers { get; set; }

    public virtual DbSet<Flight> Flights { get; set; }

    public virtual DbSet<Layover> Layovers { get; set; }

    public virtual DbSet<Passenger> Passengers { get; set; }

    public virtual DbSet<Plane> Planes { get; set; }

    public virtual DbSet<Promotion> Promotions { get; set; }

    public virtual DbSet<Reservation> Reservations { get; set; }

    public virtual DbSet<Seat> Seats { get; set; }

    public virtual DbSet<Student> Students { get; set; }

    public virtual DbSet<Suitcase> Suitcases { get; set; }

    public virtual DbSet<Ticket> Tickets { get; set; }

    public virtual DbSet<User> Users { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
        => optionsBuilder.UseNpgsql("Host=airtecdb.postgres.database.azure.com;Database=BD_AirTEC;Username=airtecadmin;Password=hdTF15-$");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Airport>(entity =>
        {
            entity.HasKey(e => e.Name).HasName("AIRPORT_pkey");

            entity.ToTable("AIRPORT");

            entity.Property(e => e.Name).HasMaxLength(50);
            entity.Property(e => e.City).HasMaxLength(60);
        });

        modelBuilder.Entity<Execution>(entity =>
        {
            entity.HasKey(e => e.Idexecution).HasName("EXECUTION_pkey");

            entity.ToTable("EXECUTION");

            entity.Property(e => e.Idexecution).HasColumnName("IDExecution");
            entity.Property(e => e.PlateNumber).HasMaxLength(50);
            entity.Property(e => e.Status).HasMaxLength(50);

            entity.HasOne(d => d.NumberFlightNavigation).WithMany(p => p.Executions)
                .HasForeignKey(d => d.NumberFlight)
                .HasConstraintName("fk_execution_flight");

            entity.HasOne(d => d.PlateNumberNavigation).WithMany(p => p.Executions)
                .HasForeignKey(d => d.PlateNumber)
                .HasConstraintName("fk_execution_plane");
        });

        modelBuilder.Entity<ExecutionLayover>(entity =>
        {
            entity.HasKey(e => new { e.Idlayover, e.Idexecution }).HasName("EXECUTION_LAYOVER_pkey");

            entity.ToTable("EXECUTION_LAYOVER");

            entity.Property(e => e.Idlayover).HasColumnName("IDLayover");
            entity.Property(e => e.Idexecution).HasColumnName("IDExecution");
        });

        modelBuilder.Entity<Flight>(entity =>
        {
            entity.HasKey(e => e.Number).HasName("FLIGHT_pkey");

            entity.ToTable("FLIGHT");

            entity.Property(e => e.Destination).HasMaxLength(50);
            entity.Property(e => e.Origin).HasMaxLength(50);

            entity.HasOne(d => d.DestinationNavigation).WithMany(p => p.FlightDestinationNavigations)
                .HasForeignKey(d => d.Destination)
                .HasConstraintName("fk_flightdestination_airport");

            entity.HasOne(d => d.OriginNavigation).WithMany(p => p.FlightOriginNavigations)
                .HasForeignKey(d => d.Origin)
                .HasConstraintName("fk_flightorigin_airport");
        });

        modelBuilder.Entity<Layover>(entity =>
        {
            entity.HasKey(e => e.Idlayover).HasName("LAYOVER_pkey");

            entity.ToTable("LAYOVER");

            entity.Property(e => e.Idlayover).HasColumnName("IDLayover");
            entity.Property(e => e.Destination).HasMaxLength(50);
            entity.Property(e => e.Origin).HasMaxLength(50);

            entity.HasOne(d => d.DestinationNavigation).WithMany(p => p.LayoverDestinationNavigations)
                .HasForeignKey(d => d.Destination)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("fk_layoverdestination_airport");

            entity.HasOne(d => d.NumberFlightNavigation).WithMany(p => p.Layovers)
                .HasForeignKey(d => d.NumberFlight)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("fk_layover_flight");

            entity.HasOne(d => d.OriginNavigation).WithMany(p => p.LayoverOriginNavigations)
                .HasForeignKey(d => d.Origin)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("fk_layoverorigin_airport");
        });

        modelBuilder.Entity<Passenger>(entity =>
        {
            entity.HasKey(e => e.Idpassenger).HasName("PASSENGER_pkey");

            entity.ToTable("PASSENGER");

            entity.Property(e => e.Idpassenger).HasColumnName("IDPassenger");
            entity.Property(e => e.Fullname).HasMaxLength(100);
        });

        modelBuilder.Entity<Plane>(entity =>
        {
            entity.HasKey(e => e.PlateNumber).HasName("PLANE_pkey");

            entity.ToTable("PLANE");

            entity.Property(e => e.PlateNumber).HasMaxLength(50);
        });

        modelBuilder.Entity<Promotion>(entity =>
        {
            entity.HasKey(e => e.Number).HasName("PROMOTION_pkey");

            entity.ToTable("PROMOTION");

            entity.Property(e => e.Idexecution).HasColumnName("IDExecution");
            entity.Property(e => e.Period).HasMaxLength(50);

            entity.HasOne(d => d.IdexecutionNavigation).WithMany(p => p.Promotions)
                .HasForeignKey(d => d.Idexecution)
                .HasConstraintName("fk_promotion_execution");
        });

        modelBuilder.Entity<Reservation>(entity =>
        {
            entity.HasKey(e => e.Idreservation).HasName("RESERVATION_pkey");

            entity.ToTable("RESERVATION");

            entity.Property(e => e.Idreservation).HasColumnName("IDReservation");
            entity.Property(e => e.Idexecution).HasColumnName("IDExecution");
            entity.Property(e => e.Iduser).HasColumnName("IDUser");

            entity.HasOne(d => d.IdexecutionNavigation).WithMany(p => p.Reservations)
                .HasForeignKey(d => d.Idexecution)
                .HasConstraintName("fk_reservation_execution");

            entity.HasOne(d => d.IduserNavigation).WithMany(p => p.Reservations)
                .HasForeignKey(d => d.Iduser)
                .HasConstraintName("fk_reservation_passenger"); 
        });

        modelBuilder.Entity<Seat>(entity =>
        {
            entity.HasKey(e => new { e.SeatNumber, e.Idexecution }).HasName("SEAT_pkey");

            entity.ToTable("SEAT");

            entity.Property(e => e.Idexecution).HasColumnName("IDExecution");

            entity.HasOne(d => d.IdexecutionNavigation).WithMany(p => p.Seats)
                .HasForeignKey(d => d.Idexecution)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("SEAT_IDExecution_fkey");
        });

        modelBuilder.Entity<Student>(entity =>
        {
            entity.HasKey(e => e.UniversityCard).HasName("STUDENT_pkey");

            entity.ToTable("STUDENT");

            entity.Property(e => e.UniversityCard).ValueGeneratedNever();
            entity.Property(e => e.Iduser).HasColumnName("IDUser");
            entity.Property(e => e.UniversityName).HasMaxLength(50);

            entity.HasOne(d => d.IduserNavigation).WithMany(p => p.Students)
                .HasForeignKey(d => d.Iduser)
                .HasConstraintName("fk_student_user");
        });

        modelBuilder.Entity<Suitcase>(entity =>
        {
            entity.HasKey(e => e.IdsuitCase).HasName("SUITCASE_pkey");

            entity.ToTable("SUITCASE");

            entity.Property(e => e.IdsuitCase).HasColumnName("IDSuitCase");
            entity.Property(e => e.Color).HasMaxLength(15);
            entity.Property(e => e.Idpassenger).HasColumnName("IDPassenger");

            entity.HasOne(d => d.IdpassengerNavigation).WithMany(p => p.Suitcases)
                .HasForeignKey(d => d.Idpassenger)
                .HasConstraintName("fk_suitcase_passenger");
        });

        modelBuilder.Entity<Ticket>(entity =>
        {
            entity.HasKey(e => e.Idticket).HasName("TICKET_pkey");

            entity.ToTable("TICKET");

            entity.Property(e => e.Idticket).HasColumnName("IDTicket");
            entity.Property(e => e.Idexecution).HasColumnName("IDExecution");
            entity.Property(e => e.Idpassenger).HasColumnName("IDPassenger");
            entity.Property(e => e.Iduser).HasColumnName("IDUser");

            entity.HasOne(d => d.IdexecutionNavigation).WithMany(p => p.Tickets)
                .HasForeignKey(d => d.Idexecution)
                .HasConstraintName("fk_ticket_execution");

            entity.HasOne(d => d.IdpassengerNavigation).WithMany(p => p.Tickets)
                .HasForeignKey(d => d.Idpassenger)
                .HasConstraintName("fk_ticket_passenger");

            entity.HasOne(d => d.IduserNavigation).WithMany(p => p.Tickets)
                .HasForeignKey(d => d.Iduser)
                .HasConstraintName("fk_ticket_user");

            entity.HasOne(d => d.Seat).WithMany(p => p.Tickets)
                .HasForeignKey(d => new { d.SeatNumber, d.Idexecution })
                .HasConstraintName("fk_ticket_seat");
        });

        modelBuilder.Entity<User>(entity =>
        {
            entity.HasKey(e => e.Iduser).HasName("USER_pkey");

            entity.ToTable("USER");

            entity.Property(e => e.Iduser).HasColumnName("IDUser");
            entity.Property(e => e.Email).HasMaxLength(25);
            entity.Property(e => e.Fullname).HasMaxLength(100);
            entity.Property(e => e.Idpassenger).HasColumnName("IDPassenger");
            entity.Property(e => e.Password).HasMaxLength(15);

            entity.HasOne(d => d.IdpassengerNavigation).WithMany(p => p.Users)
                .HasForeignKey(d => d.Idpassenger)
                .HasConstraintName("fk_user_passenger");
        });
        modelBuilder.HasSequence("id_seq");
        modelBuilder.HasSequence("passenger_id_seq");

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
