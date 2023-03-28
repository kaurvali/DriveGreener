import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Vehicle } from '../_models/vehicle.model';
import { StorageService } from '../_services/storage.service';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.scss']
})
export class VehiclesComponent implements OnInit{
  
  constructor(private userService: UserService, private storageService: StorageService, private router: Router) { }

  public vehicles: Vehicle[] = [];

  ngOnInit(): void {
    const userId: number = this.storageService.getUser().id
    this.userService.getVehiclesForUser(userId).subscribe(res => {
          this.vehicles = res;
    });    
  }

  addVehicle(): void{
    this.router.navigate(["/add-vehicle"]);
  }

  openFillups(vehicleId: number): void{
    this.router.navigate(["/fill-ups", vehicleId]);
  }


}
