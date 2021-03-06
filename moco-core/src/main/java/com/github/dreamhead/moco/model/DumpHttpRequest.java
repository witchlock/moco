package com.github.dreamhead.moco.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.github.dreamhead.moco.HttpRequest;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

import static com.google.common.collect.ImmutableMap.copyOf;

@JsonDeserialize(builder = DumpHttpRequest.Builder.class)
public class DumpHttpRequest extends DumpMessage implements HttpRequest {
    private final ImmutableMap<String, String> queries;
    private final String method;
    private final String uri;

    private DumpHttpRequest(String version, String content, String method, String uri,
                            ImmutableMap<String, String> headers, ImmutableMap<String, String> queries) {
        super(version, content, headers);
        this.method = method;
        this.uri = uri;
        this.queries = queries;
    }

    @Override
    public String getUri() {
        return uri;
    }

    public ImmutableMap<String, String> getQueries() {
        return queries;
    }

    public String getMethod() {
        return method;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), queries, method, uri);
    }

    @Override
    public String toString() {
        return new HttpRequestDelegate(this).toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String version;
        private String content;
        private ImmutableMap<String, String> headers;
        private ImmutableMap<String, String> queries;
        private String method;
        private String uri;

        public Builder withVersion(String version) {
            this.version = version;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withHeaders(Map<String, String> headers) {
            if (headers != null) {
                this.headers = copyOf(headers);
            }

            return this;
        }

        public Builder withQueries(Map<String, String> queries) {
            if (queries != null) {
                this.queries = copyOf(queries);
            }

            return this;
        }

        public Builder withMethod(String method) {
            this.method = method;
            return this;
        }

        public Builder withUri(String uri) {
            this.uri = uri;
            return this;
        }

        public DumpHttpRequest build() {
            return new DumpHttpRequest(version, content, method, uri, headers, queries);
        }
    }
}
