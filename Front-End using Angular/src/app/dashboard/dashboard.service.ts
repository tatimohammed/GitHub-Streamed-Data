import { Injectable } from '@angular/core';
import { Data } from './dashboard';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';

@Injectable()
export class DataService {
    constructor(private http: HttpClient) { }

    fetchData(): Observable<Data> {
        const url = 'http://localhost:8080/stream_data';
        return this.http.get(url).pipe(
            map((response: any) => {
                let data = new Data();
                data.repos = response.repos;
                data.langs = response.languages;
                data.nums = response.total_lang;
                data.months = response.months;
                return data;
            })
        );
    }
}
