import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { AppComponent } from './app/app.component';
import { routes } from './app/app.routes';
import { basicAuthInterceptor } from './app/interceptors/basic-auth.interceptor';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(
      withInterceptors([basicAuthInterceptor])
    ),
    provideRouter(routes)
  ]
}).catch(err => console.error(err));
