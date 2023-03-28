import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { BoardUserComponent } from './board-user/board-user.component';
import { AddVehicleComponent } from './add-vehicle/add-vehicle.component';
import { AddFillingComponent } from './add-filling/add-filling.component';
import { AuthGuard } from './_guards/auth.guard';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { FillUpsComponent } from './fill-ups/fill-ups.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent },
  { path: 'add-vehicle', component: AddVehicleComponent, data:{requiresLogin: true}, canActivate: [AuthGuard]},
  { path: 'add-fillup/:id', component: AddFillingComponent, data:{requiresLogin: true}, canActivate: [AuthGuard]},
  { path: 'fill-ups/:id', component: FillUpsComponent, data:{requiresLogin: true}, canActivate: [AuthGuard]},
  { path: 'profile', component: ProfileComponent, data:{requiresLogin: true}, canActivate: [AuthGuard]},
  { path: 'user', component: BoardUserComponent, data:{requiresLogin: true}, canActivate: [AuthGuard]},
  { path: 'vehicles', component: VehiclesComponent, data:{requiresLogin: true}, canActivate: [AuthGuard]},
  { path: '', redirectTo: 'home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }