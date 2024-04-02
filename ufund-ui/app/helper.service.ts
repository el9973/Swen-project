import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Hero } from './hero';
import { MessageService } from './message.service';
import { Helper } from './helper';


@Injectable({ providedIn: 'root' })
export class HelperService {

  private helperUrl = 'api/helpers';  // URL to web api

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }



    /** GET hero by id. Return `undefined` when id not found */
  getHelperNo404<Data>(name: string): Observable<Helper> {
    const url = `${this.helperUrl}/?name=${name}`;
    return this.http.get<Helper[]>(url)
      .pipe(
        map(helper => helper[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} helper name=${name}`);
        }),
        catchError(this.handleError<Helper>(`getHelper name=${name}`))
      );
  }


  //////// Save methods //////////

  /** POST: add a new hero to the server */
  addHelper(helper: Helper): Observable<Helper> {
    return this.http.post<Helper>(this.helperUrl, helper, this.httpOptions).pipe(
      tap((newHelper: Helper) => this.log(`added helper w/ name=${newHelper.name}`)),
      catchError(this.handleError<Helper>('addHelper'))
    );
  }

  /** PUT: update the hero on the server */
  updateHelper(helper: Helper): Observable<any> {
    return this.http.put(this.helperUrl, helper, this.httpOptions).pipe(
      tap(_ => this.log(`updated hero name=${helper.name}`)),
      catchError(this.handleError<any>('updateHelper'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`HelperService: ${message}`);
  }
}