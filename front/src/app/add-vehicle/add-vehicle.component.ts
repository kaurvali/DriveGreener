import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms'


@Component({
  selector: 'app-add-vehicle',
  templateUrl: './add-vehicle.component.html',
  styleUrls: ['./add-vehicle.component.scss']
})
export class AddVehicleComponent{
  vehicle = new FormGroup({
    fuel: new FormControl<VehicleType | null>(null),
    year: new FormControl<number | null>(new Date().getFullYear()),
    make: new FormControl(""),
    model: new FormControl(""),
    trim: new FormControl(""),
    engine: new FormControl(""),
    power: new FormControl(""),
    transmission: new FormControl<TransmissionType | null>(null),
    drivetrain: new FormControl<DrivetrainType | null>(null),
    public: new FormControl(true),
  });
  
  maxYear: number;

  constructor() {
    this.maxYear = new Date().getFullYear();
  };

  onSubmit(){};

}


enum VehicleType {
  PETROL = "PETROL",
  DIESEL = "DIESEL",
  ELECTRICITY = "ELECTRICITY",
}

enum TransmissionType {
  AUTOMATIC = "AUTOMATIC",
  MANUAL = "MANUAL",
  SEMI_AUTOMATIC = "SEMI_AUTOMATIC",
  DIRECT = "DIRECT",
}

enum DrivetrainType {
  AWD = "AWD",
  FWD = "FWD",
  RWD = "RWD",
}
