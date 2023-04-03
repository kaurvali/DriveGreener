import { VehicleStatistics } from "../_dto/vehicle.statistics.dto";

export class Vehicle {
    id!: number;
    make?: string;
    model?: string;
    trim?: string;
    year?: number;
    engine?: string;
    power?: number;
    transmission?: string;
    drivetrain?: string;
    vehicleType?: string;
    isPublic?: boolean;
    user?: number;
    statistics?: VehicleStatistics;
  }