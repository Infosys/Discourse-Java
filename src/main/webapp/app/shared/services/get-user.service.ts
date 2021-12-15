import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { IUserResponse } from '../model/user/user-response.model';
import { IUser } from '../model/user/user.model';
import { UserService } from '../services/user.service';

@Injectable({
  providedIn: 'root',
})
export class GetUserService {
  currentlyLoggedInUser: BehaviorSubject<IUser> = new BehaviorSubject<IUser>({});

  constructor(private userService: UserService) {}

  getUser(): void {
    this.userService.getCurrentlyLoggedInUser().subscribe(
      (res: HttpResponse<IUserResponse>) => {
        // console.error(res);
        this.currentlyLoggedInUser.next(res.body!.user!);
      },
      (err: HttpErrorResponse) => {
        console.error(err);
      }
    );
  }
}
