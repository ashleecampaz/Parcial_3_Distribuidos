import { ApplicationConfig, importProvidersFrom } from '@angular/core';
import { provideRouter, CanActivateFn } from '@angular/router';
import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { HttpClientModule, withFetch, withInterceptors } from '@angular/common/http';
import { provideHttpClient } from '@angular/common/http';

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes), 
    provideClientHydration(), 
    importProvidersFrom(HttpClientModule),
    provideHttpClient()
  ]
};