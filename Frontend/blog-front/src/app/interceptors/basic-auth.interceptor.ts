import { HttpRequest, HttpHandlerFn, HttpInterceptorFn } from '@angular/common/http';
import { Observable } from 'rxjs';

export const basicAuthInterceptor: HttpInterceptorFn = (
  req: HttpRequest<unknown>,
  next: HttpHandlerFn
): Observable<any> => {
  const token = localStorage.getItem('basicAuth');
  if (token) {
    req = req.clone({
      setHeaders: { Authorization: 'Basic ' + token }
    });
  }
  return next(req);
};
