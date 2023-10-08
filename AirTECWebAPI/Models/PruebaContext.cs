using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore;

namespace AirTECWebAPI.Models;

public partial class PruebaContext : DbContext
{
    public PruebaContext()
    {
    }

    public PruebaContext(DbContextOptions<PruebaContext> options)
        : base(options)
    {
    }

    public virtual DbSet<Department> Departments { get; set; }

    public virtual DbSet<Employee> Employees { get; set; }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see http://go.microsoft.com/fwlink/?LinkId=723263.
        => optionsBuilder.UseNpgsql("Host=airtecdb.postgres.database.azure.com;Database=prueba;Username=airtecadmin;Password=hdTF15-$");

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.Entity<Department>(entity =>
        {
            entity.HasKey(e => e.Dnumber).HasName("department_pkey");

            entity.ToTable("department");

            entity.Property(e => e.Dnumber)
                .ValueGeneratedNever()
                .HasColumnName("dnumber");
            entity.Property(e => e.Dname)
                .HasMaxLength(15)
                .HasColumnName("dname");
        });

        modelBuilder.Entity<Employee>(entity =>
        {
            entity.HasKey(e => e.Ssn).HasName("employee_pkey");

            entity.ToTable("employee");

            entity.Property(e => e.Ssn)
                .HasMaxLength(9)
                .IsFixedLength()
                .HasColumnName("ssn");
            entity.Property(e => e.Dno).HasColumnName("dno");
            entity.Property(e => e.Fname)
                .HasMaxLength(15)
                .HasColumnName("fname");

            entity.HasOne(d => d.DnoNavigation).WithMany(p => p.Employees)
                .HasForeignKey(d => d.Dno)
                .OnDelete(DeleteBehavior.ClientSetNull)
                .HasConstraintName("employee_department_fk");
        });

        OnModelCreatingPartial(modelBuilder);
    }

    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
}
