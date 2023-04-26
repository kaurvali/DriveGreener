import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';
import { StorageService } from '../_services/storage.service';
import { Router } from '@angular/router';
import { Vehicle } from '../_models/vehicle.model';
import { UserStatistics } from '../_dto/user.statistics.dto';
import { VehicleService } from '../_services/vehicle.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  content?: string;
  isLoggedIn: boolean = false;
  username?: string;
  userId?: number;
  vehicles?: Vehicle[] = [];
  userStatistics?: UserStatistics;

  constructor(private userService: UserService, private storageService: StorageService, private router: Router) { }

  ngOnInit(): void {
    this.isLoggedIn = this.storageService.isLoggedIn();
    if (this.isLoggedIn) {
      console.log("test")
      const user = this.storageService.getUser();
      this.username = user.username;
      this.userId = user.id;
      console.log(user)

      this.userService.getVehiclesForUser(user.id).subscribe(res => {
        this.vehicles = res;
      })

      this.userService.getTotalStatisticsForUser(user.id).subscribe(res => {
        this.userStatistics = res;
        console.log(this.userStatistics)
      })


    }
  }

  getVehicleName(vehicle: Vehicle){
    return vehicle.year + " " + vehicle.make + " " + vehicle.model + " " + vehicle.trim;
  }

  canShowElectric(): boolean{
    if (this.userStatistics){
      if (this.userStatistics.energyConsumed != 0)
        return true;
    }
    return false;
  }

  canShowICE(){
    if (this.userStatistics){
      if (this.userStatistics.fuelConsumed != 0)
        return true;
    }
    return false;
  }

  goToVehicles(){
    this.router.navigate(["vehicles"]);
  }
}