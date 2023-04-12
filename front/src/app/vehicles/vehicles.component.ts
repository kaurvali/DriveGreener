import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Vehicle } from '../_models/vehicle.model';
import { StorageService } from '../_services/storage.service';
import { UserService } from '../_services/user.service';
import { VehicleService } from '../_services/vehicle.service';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.scss']
})
export class VehiclesComponent implements OnInit{
  
  constructor(private userService: UserService, private storageService: StorageService, private vehicleService: VehicleService, private router: Router) { }

  public vehicles: Vehicle[] = [];

  ngOnInit(): void {
    const userId: number = this.storageService.getUser().id
    this.userService.getVehiclesForUser(userId).subscribe(res => {
      this.vehicles = res;
      this.addStatistics();
    }); 
  }

  addVehicle(): void{
    this.router.navigate(["/add-vehicle"]);
  }

  openFillups(vehicleId: number): void{
    this.router.navigate(["/fill-ups", vehicleId]);
  }

  addStatistics(){
    for (let vehicle of this.vehicles){
      this.vehicleService.getStatisticsForVehicle(vehicle.id).subscribe(res => {
        vehicle.statistics = res;
        console.log(vehicle)
      });
    };
  }

  getName(vehicle: Vehicle): string {
    return vehicle.year + " " + vehicle.make + " " + vehicle.model + " " + vehicle.trim;
  }

  getSpecs(vehicle: Vehicle): string {
    return vehicle.engine + " " + vehicle.power + "HP " + vehicle.transmission + " " + vehicle.drivetrain;
  }

  getFuelUnit(vehicle: Vehicle){
    if(vehicle.vehicleType == "ELECTRIC"){
      return "kWh"
    }
    return "l"
  }
}
