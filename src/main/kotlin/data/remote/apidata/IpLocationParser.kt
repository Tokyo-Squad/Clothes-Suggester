package data.remote.apidata

import data.remote.dto.IpCoordinate

interface IpLocationParser {
    fun parse(body: String): IpCoordinate
}