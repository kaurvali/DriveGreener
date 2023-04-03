import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms'
import { Router } from '@angular/router';
import { StorageService } from '../_services/storage.service';
import { VehicleService } from '../_services/vehicle.service';


@Component({
  selector: 'app-add-vehicle',
  templateUrl: './add-vehicle.component.html',
  styleUrls: ['./add-vehicle.component.scss']
})
export class AddVehicleComponent implements OnInit{
    
  maxYear: number;
  userId: number = -1;

  constructor(private vehicleService: VehicleService, private storageService: StorageService, private router: Router) {
    this.maxYear = new Date().getFullYear();
  };

  ngOnInit(): void {
    if (this.storageService.isLoggedIn()) {
      this.userId = this.storageService.getUser().id;
    }
  }

  vehicle = new FormGroup({
    make: new FormControl(),
    model: new FormControl(),
    trim: new FormControl(),
    year: new FormControl<number | null>(new Date().getFullYear()),
    engine: new FormControl(),
    power: new FormControl<number| null>(null),
    transmission: new FormControl<TransmissionType | null>(null),
    drivetrain: new FormControl<DrivetrainType | null>(null),
    vehicleType: new FormControl<VehicleType | null>(null),
    isPublic: new FormControl(true),
    userId: new FormControl(this.storageService.getUser().id)
  });
  errorMsg: String = "";

  onSubmit(){
    this.vehicleService.addVehicle(this.vehicle).subscribe((res) => {
      this.router.navigate(['/vehicles']);
    },
    (err)=>{
      this.errorMsg = "Could not add vehicle! Please try again!";
    });
  };
}


enum VehicleType {
  PETROL = "PETROL",
  DIESEL = "DIESEL",
  ELECTRIC = "ELECTRIC",
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
