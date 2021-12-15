import { Injectable } from '@angular/core';
import { MediaObserver } from '@angular/flex-layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

interface ScreenSizes {
  xs?: boolean;
  sm?: boolean;
  md?: boolean;
  lg?: boolean;
  xl?: boolean;
  xxl?: boolean;
}
@Injectable({
  providedIn: 'root',
})
export class ViewportChangeDetector {
  constructor(private mediaObserver: MediaObserver) {}

  detectScreenSize(sizes: ScreenSizes): Observable<boolean | undefined> {
    return this.mediaObserver
      .asObservable()
      .pipe(
        map(
          () =>
            (this.mediaObserver.isActive('bootstrapXs') && sizes.xs) ||
            (this.mediaObserver.isActive('bootstrapSm') && sizes.sm) ||
            (this.mediaObserver.isActive('bootstrapMd') && sizes.md) ||
            (this.mediaObserver.isActive('bootstrapLg') && sizes.lg) ||
            (this.mediaObserver.isActive('bootstrapXl') && sizes.xl) ||
            (this.mediaObserver.isActive('bootstrapXxl') && sizes.xxl)
        )
      );
  }
}
