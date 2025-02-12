// Generated by the protocol buffer compiler.  DO NOT EDIT!
// NO CHECKED-IN PROTOBUF GENCODE
// source: com/zkart/dbFiles/proto/baseUser.proto
// Protobuf Java Version: 4.29.3

package com.zkart.model;

public final class BaseUserProto {
  private BaseUserProto() {}
  static {
    com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
      com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
      /* major= */ 4,
      /* minor= */ 29,
      /* patch= */ 3,
      /* suffix= */ "",
      BaseUserProto.class.getName());
  }
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface BaseUserOrBuilder extends
      // @@protoc_insertion_point(interface_extends:BaseUser)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>int32 id = 1;</code>
     * @return The id.
     */
    int getId();

    /**
     * <code>string email = 2;</code>
     * @return The email.
     */
    java.lang.String getEmail();
    /**
     * <code>string email = 2;</code>
     * @return The bytes for email.
     */
    com.google.protobuf.ByteString
        getEmailBytes();

    /**
     * <code>string fullname = 4;</code>
     * @return The fullname.
     */
    java.lang.String getFullname();
    /**
     * <code>string fullname = 4;</code>
     * @return The bytes for fullname.
     */
    com.google.protobuf.ByteString
        getFullnameBytes();

    /**
     * <code>string password = 5;</code>
     * @return The password.
     */
    java.lang.String getPassword();
    /**
     * <code>string password = 5;</code>
     * @return The bytes for password.
     */
    com.google.protobuf.ByteString
        getPasswordBytes();

    /**
     * <code>repeated string prePasswords = 8;</code>
     * @return A list containing the prePasswords.
     */
    java.util.List<java.lang.String>
        getPrePasswordsList();
    /**
     * <code>repeated string prePasswords = 8;</code>
     * @return The count of prePasswords.
     */
    int getPrePasswordsCount();
    /**
     * <code>repeated string prePasswords = 8;</code>
     * @param index The index of the element to return.
     * @return The prePasswords at the given index.
     */
    java.lang.String getPrePasswords(int index);
    /**
     * <code>repeated string prePasswords = 8;</code>
     * @param index The index of the value to return.
     * @return The bytes of the prePasswords at the given index.
     */
    com.google.protobuf.ByteString
        getPrePasswordsBytes(int index);
  }
  /**
   * Protobuf type {@code BaseUser}
   */
  public static final class BaseUser extends
      com.google.protobuf.GeneratedMessage implements
      // @@protoc_insertion_point(message_implements:BaseUser)
      BaseUserOrBuilder {
  private static final long serialVersionUID = 0L;
    static {
      com.google.protobuf.RuntimeVersion.validateProtobufGencodeVersion(
        com.google.protobuf.RuntimeVersion.RuntimeDomain.PUBLIC,
        /* major= */ 4,
        /* minor= */ 29,
        /* patch= */ 3,
        /* suffix= */ "",
        BaseUser.class.getName());
    }
    // Use BaseUser.newBuilder() to construct.
    private BaseUser(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
      super(builder);
    }
    private BaseUser() {
      email_ = "";
      fullname_ = "";
      password_ = "";
      prePasswords_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
    }

    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.zkart.model.BaseUserProto.internal_static_BaseUser_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.zkart.model.BaseUserProto.internal_static_BaseUser_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.zkart.model.BaseUserProto.BaseUser.class, com.zkart.model.BaseUserProto.BaseUser.Builder.class);
    }

    public static final int ID_FIELD_NUMBER = 1;
    private int id_ = 0;
    /**
     * <code>int32 id = 1;</code>
     * @return The id.
     */
    @java.lang.Override
    public int getId() {
      return id_;
    }

    public static final int EMAIL_FIELD_NUMBER = 2;
    @SuppressWarnings("serial")
    private volatile java.lang.Object email_ = "";
    /**
     * <code>string email = 2;</code>
     * @return The email.
     */
    @java.lang.Override
    public java.lang.String getEmail() {
      java.lang.Object ref = email_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        email_ = s;
        return s;
      }
    }
    /**
     * <code>string email = 2;</code>
     * @return The bytes for email.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getEmailBytes() {
      java.lang.Object ref = email_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        email_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int FULLNAME_FIELD_NUMBER = 4;
    @SuppressWarnings("serial")
    private volatile java.lang.Object fullname_ = "";
    /**
     * <code>string fullname = 4;</code>
     * @return The fullname.
     */
    @java.lang.Override
    public java.lang.String getFullname() {
      java.lang.Object ref = fullname_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        fullname_ = s;
        return s;
      }
    }
    /**
     * <code>string fullname = 4;</code>
     * @return The bytes for fullname.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getFullnameBytes() {
      java.lang.Object ref = fullname_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        fullname_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PASSWORD_FIELD_NUMBER = 5;
    @SuppressWarnings("serial")
    private volatile java.lang.Object password_ = "";
    /**
     * <code>string password = 5;</code>
     * @return The password.
     */
    @java.lang.Override
    public java.lang.String getPassword() {
      java.lang.Object ref = password_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        password_ = s;
        return s;
      }
    }
    /**
     * <code>string password = 5;</code>
     * @return The bytes for password.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString
        getPasswordBytes() {
      java.lang.Object ref = password_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        password_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int PREPASSWORDS_FIELD_NUMBER = 8;
    @SuppressWarnings("serial")
    private com.google.protobuf.LazyStringArrayList prePasswords_ =
        com.google.protobuf.LazyStringArrayList.emptyList();
    /**
     * <code>repeated string prePasswords = 8;</code>
     * @return A list containing the prePasswords.
     */
    public com.google.protobuf.ProtocolStringList
        getPrePasswordsList() {
      return prePasswords_;
    }
    /**
     * <code>repeated string prePasswords = 8;</code>
     * @return The count of prePasswords.
     */
    public int getPrePasswordsCount() {
      return prePasswords_.size();
    }
    /**
     * <code>repeated string prePasswords = 8;</code>
     * @param index The index of the element to return.
     * @return The prePasswords at the given index.
     */
    public java.lang.String getPrePasswords(int index) {
      return prePasswords_.get(index);
    }
    /**
     * <code>repeated string prePasswords = 8;</code>
     * @param index The index of the value to return.
     * @return The bytes of the prePasswords at the given index.
     */
    public com.google.protobuf.ByteString
        getPrePasswordsBytes(int index) {
      return prePasswords_.getByteString(index);
    }

    private byte memoizedIsInitialized = -1;
    @java.lang.Override
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    @java.lang.Override
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (id_ != 0) {
        output.writeInt32(1, id_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(email_)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 2, email_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(fullname_)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 4, fullname_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(password_)) {
        com.google.protobuf.GeneratedMessage.writeString(output, 5, password_);
      }
      for (int i = 0; i < prePasswords_.size(); i++) {
        com.google.protobuf.GeneratedMessage.writeString(output, 8, prePasswords_.getRaw(i));
      }
      getUnknownFields().writeTo(output);
    }

    @java.lang.Override
    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (id_ != 0) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, id_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(email_)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(2, email_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(fullname_)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(4, fullname_);
      }
      if (!com.google.protobuf.GeneratedMessage.isStringEmpty(password_)) {
        size += com.google.protobuf.GeneratedMessage.computeStringSize(5, password_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < prePasswords_.size(); i++) {
          dataSize += computeStringSizeNoTag(prePasswords_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getPrePasswordsList().size();
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.zkart.model.BaseUserProto.BaseUser)) {
        return super.equals(obj);
      }
      com.zkart.model.BaseUserProto.BaseUser other = (com.zkart.model.BaseUserProto.BaseUser) obj;

      if (getId()
          != other.getId()) return false;
      if (!getEmail()
          .equals(other.getEmail())) return false;
      if (!getFullname()
          .equals(other.getFullname())) return false;
      if (!getPassword()
          .equals(other.getPassword())) return false;
      if (!getPrePasswordsList()
          .equals(other.getPrePasswordsList())) return false;
      if (!getUnknownFields().equals(other.getUnknownFields())) return false;
      return true;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + ID_FIELD_NUMBER;
      hash = (53 * hash) + getId();
      hash = (37 * hash) + EMAIL_FIELD_NUMBER;
      hash = (53 * hash) + getEmail().hashCode();
      hash = (37 * hash) + FULLNAME_FIELD_NUMBER;
      hash = (53 * hash) + getFullname().hashCode();
      hash = (37 * hash) + PASSWORD_FIELD_NUMBER;
      hash = (53 * hash) + getPassword().hashCode();
      if (getPrePasswordsCount() > 0) {
        hash = (37 * hash) + PREPASSWORDS_FIELD_NUMBER;
        hash = (53 * hash) + getPrePasswordsList().hashCode();
      }
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.zkart.model.BaseUserProto.BaseUser parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.zkart.model.BaseUserProto.BaseUser parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.zkart.model.BaseUserProto.BaseUser parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.zkart.model.BaseUserProto.BaseUser parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.zkart.model.BaseUserProto.BaseUser parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.zkart.model.BaseUserProto.BaseUser parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.zkart.model.BaseUserProto.BaseUser parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static com.zkart.model.BaseUserProto.BaseUser parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public static com.zkart.model.BaseUserProto.BaseUser parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input);
    }

    public static com.zkart.model.BaseUserProto.BaseUser parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.zkart.model.BaseUserProto.BaseUser parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input);
    }
    public static com.zkart.model.BaseUserProto.BaseUser parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessage
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    @java.lang.Override
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.zkart.model.BaseUserProto.BaseUser prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    @java.lang.Override
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessage.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code BaseUser}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:BaseUser)
        com.zkart.model.BaseUserProto.BaseUserOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.zkart.model.BaseUserProto.internal_static_BaseUser_descriptor;
      }

      @java.lang.Override
      protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.zkart.model.BaseUserProto.internal_static_BaseUser_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.zkart.model.BaseUserProto.BaseUser.class, com.zkart.model.BaseUserProto.BaseUser.Builder.class);
      }

      // Construct using com.zkart.model.BaseUserProto.BaseUser.newBuilder()
      private Builder() {

      }

      private Builder(
          com.google.protobuf.GeneratedMessage.BuilderParent parent) {
        super(parent);

      }
      @java.lang.Override
      public Builder clear() {
        super.clear();
        bitField0_ = 0;
        id_ = 0;
        email_ = "";
        fullname_ = "";
        password_ = "";
        prePasswords_ =
            com.google.protobuf.LazyStringArrayList.emptyList();
        return this;
      }

      @java.lang.Override
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.zkart.model.BaseUserProto.internal_static_BaseUser_descriptor;
      }

      @java.lang.Override
      public com.zkart.model.BaseUserProto.BaseUser getDefaultInstanceForType() {
        return com.zkart.model.BaseUserProto.BaseUser.getDefaultInstance();
      }

      @java.lang.Override
      public com.zkart.model.BaseUserProto.BaseUser build() {
        com.zkart.model.BaseUserProto.BaseUser result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      @java.lang.Override
      public com.zkart.model.BaseUserProto.BaseUser buildPartial() {
        com.zkart.model.BaseUserProto.BaseUser result = new com.zkart.model.BaseUserProto.BaseUser(this);
        if (bitField0_ != 0) { buildPartial0(result); }
        onBuilt();
        return result;
      }

      private void buildPartial0(com.zkart.model.BaseUserProto.BaseUser result) {
        int from_bitField0_ = bitField0_;
        if (((from_bitField0_ & 0x00000001) != 0)) {
          result.id_ = id_;
        }
        if (((from_bitField0_ & 0x00000002) != 0)) {
          result.email_ = email_;
        }
        if (((from_bitField0_ & 0x00000004) != 0)) {
          result.fullname_ = fullname_;
        }
        if (((from_bitField0_ & 0x00000008) != 0)) {
          result.password_ = password_;
        }
        if (((from_bitField0_ & 0x00000010) != 0)) {
          prePasswords_.makeImmutable();
          result.prePasswords_ = prePasswords_;
        }
      }

      @java.lang.Override
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.zkart.model.BaseUserProto.BaseUser) {
          return mergeFrom((com.zkart.model.BaseUserProto.BaseUser)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.zkart.model.BaseUserProto.BaseUser other) {
        if (other == com.zkart.model.BaseUserProto.BaseUser.getDefaultInstance()) return this;
        if (other.getId() != 0) {
          setId(other.getId());
        }
        if (!other.getEmail().isEmpty()) {
          email_ = other.email_;
          bitField0_ |= 0x00000002;
          onChanged();
        }
        if (!other.getFullname().isEmpty()) {
          fullname_ = other.fullname_;
          bitField0_ |= 0x00000004;
          onChanged();
        }
        if (!other.getPassword().isEmpty()) {
          password_ = other.password_;
          bitField0_ |= 0x00000008;
          onChanged();
        }
        if (!other.prePasswords_.isEmpty()) {
          if (prePasswords_.isEmpty()) {
            prePasswords_ = other.prePasswords_;
            bitField0_ |= 0x00000010;
          } else {
            ensurePrePasswordsIsMutable();
            prePasswords_.addAll(other.prePasswords_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.getUnknownFields());
        onChanged();
        return this;
      }

      @java.lang.Override
      public final boolean isInitialized() {
        return true;
      }

      @java.lang.Override
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        if (extensionRegistry == null) {
          throw new java.lang.NullPointerException();
        }
        try {
          boolean done = false;
          while (!done) {
            int tag = input.readTag();
            switch (tag) {
              case 0:
                done = true;
                break;
              case 8: {
                id_ = input.readInt32();
                bitField0_ |= 0x00000001;
                break;
              } // case 8
              case 18: {
                email_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000002;
                break;
              } // case 18
              case 34: {
                fullname_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000004;
                break;
              } // case 34
              case 42: {
                password_ = input.readStringRequireUtf8();
                bitField0_ |= 0x00000008;
                break;
              } // case 42
              case 66: {
                java.lang.String s = input.readStringRequireUtf8();
                ensurePrePasswordsIsMutable();
                prePasswords_.add(s);
                break;
              } // case 66
              default: {
                if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                  done = true; // was an endgroup tag
                }
                break;
              } // default:
            } // switch (tag)
          } // while (!done)
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.unwrapIOException();
        } finally {
          onChanged();
        } // finally
        return this;
      }
      private int bitField0_;

      private int id_ ;
      /**
       * <code>int32 id = 1;</code>
       * @return The id.
       */
      @java.lang.Override
      public int getId() {
        return id_;
      }
      /**
       * <code>int32 id = 1;</code>
       * @param value The id to set.
       * @return This builder for chaining.
       */
      public Builder setId(int value) {

        id_ = value;
        bitField0_ |= 0x00000001;
        onChanged();
        return this;
      }
      /**
       * <code>int32 id = 1;</code>
       * @return This builder for chaining.
       */
      public Builder clearId() {
        bitField0_ = (bitField0_ & ~0x00000001);
        id_ = 0;
        onChanged();
        return this;
      }

      private java.lang.Object email_ = "";
      /**
       * <code>string email = 2;</code>
       * @return The email.
       */
      public java.lang.String getEmail() {
        java.lang.Object ref = email_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          email_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string email = 2;</code>
       * @return The bytes for email.
       */
      public com.google.protobuf.ByteString
          getEmailBytes() {
        java.lang.Object ref = email_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          email_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string email = 2;</code>
       * @param value The email to set.
       * @return This builder for chaining.
       */
      public Builder setEmail(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        email_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }
      /**
       * <code>string email = 2;</code>
       * @return This builder for chaining.
       */
      public Builder clearEmail() {
        email_ = getDefaultInstance().getEmail();
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
        return this;
      }
      /**
       * <code>string email = 2;</code>
       * @param value The bytes for email to set.
       * @return This builder for chaining.
       */
      public Builder setEmailBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        email_ = value;
        bitField0_ |= 0x00000002;
        onChanged();
        return this;
      }

      private java.lang.Object fullname_ = "";
      /**
       * <code>string fullname = 4;</code>
       * @return The fullname.
       */
      public java.lang.String getFullname() {
        java.lang.Object ref = fullname_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          fullname_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string fullname = 4;</code>
       * @return The bytes for fullname.
       */
      public com.google.protobuf.ByteString
          getFullnameBytes() {
        java.lang.Object ref = fullname_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          fullname_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string fullname = 4;</code>
       * @param value The fullname to set.
       * @return This builder for chaining.
       */
      public Builder setFullname(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        fullname_ = value;
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }
      /**
       * <code>string fullname = 4;</code>
       * @return This builder for chaining.
       */
      public Builder clearFullname() {
        fullname_ = getDefaultInstance().getFullname();
        bitField0_ = (bitField0_ & ~0x00000004);
        onChanged();
        return this;
      }
      /**
       * <code>string fullname = 4;</code>
       * @param value The bytes for fullname to set.
       * @return This builder for chaining.
       */
      public Builder setFullnameBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        fullname_ = value;
        bitField0_ |= 0x00000004;
        onChanged();
        return this;
      }

      private java.lang.Object password_ = "";
      /**
       * <code>string password = 5;</code>
       * @return The password.
       */
      public java.lang.String getPassword() {
        java.lang.Object ref = password_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          password_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string password = 5;</code>
       * @return The bytes for password.
       */
      public com.google.protobuf.ByteString
          getPasswordBytes() {
        java.lang.Object ref = password_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          password_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string password = 5;</code>
       * @param value The password to set.
       * @return This builder for chaining.
       */
      public Builder setPassword(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        password_ = value;
        bitField0_ |= 0x00000008;
        onChanged();
        return this;
      }
      /**
       * <code>string password = 5;</code>
       * @return This builder for chaining.
       */
      public Builder clearPassword() {
        password_ = getDefaultInstance().getPassword();
        bitField0_ = (bitField0_ & ~0x00000008);
        onChanged();
        return this;
      }
      /**
       * <code>string password = 5;</code>
       * @param value The bytes for password to set.
       * @return This builder for chaining.
       */
      public Builder setPasswordBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        password_ = value;
        bitField0_ |= 0x00000008;
        onChanged();
        return this;
      }

      private com.google.protobuf.LazyStringArrayList prePasswords_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
      private void ensurePrePasswordsIsMutable() {
        if (!prePasswords_.isModifiable()) {
          prePasswords_ = new com.google.protobuf.LazyStringArrayList(prePasswords_);
        }
        bitField0_ |= 0x00000010;
      }
      /**
       * <code>repeated string prePasswords = 8;</code>
       * @return A list containing the prePasswords.
       */
      public com.google.protobuf.ProtocolStringList
          getPrePasswordsList() {
        prePasswords_.makeImmutable();
        return prePasswords_;
      }
      /**
       * <code>repeated string prePasswords = 8;</code>
       * @return The count of prePasswords.
       */
      public int getPrePasswordsCount() {
        return prePasswords_.size();
      }
      /**
       * <code>repeated string prePasswords = 8;</code>
       * @param index The index of the element to return.
       * @return The prePasswords at the given index.
       */
      public java.lang.String getPrePasswords(int index) {
        return prePasswords_.get(index);
      }
      /**
       * <code>repeated string prePasswords = 8;</code>
       * @param index The index of the value to return.
       * @return The bytes of the prePasswords at the given index.
       */
      public com.google.protobuf.ByteString
          getPrePasswordsBytes(int index) {
        return prePasswords_.getByteString(index);
      }
      /**
       * <code>repeated string prePasswords = 8;</code>
       * @param index The index to set the value at.
       * @param value The prePasswords to set.
       * @return This builder for chaining.
       */
      public Builder setPrePasswords(
          int index, java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        ensurePrePasswordsIsMutable();
        prePasswords_.set(index, value);
        bitField0_ |= 0x00000010;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string prePasswords = 8;</code>
       * @param value The prePasswords to add.
       * @return This builder for chaining.
       */
      public Builder addPrePasswords(
          java.lang.String value) {
        if (value == null) { throw new NullPointerException(); }
        ensurePrePasswordsIsMutable();
        prePasswords_.add(value);
        bitField0_ |= 0x00000010;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string prePasswords = 8;</code>
       * @param values The prePasswords to add.
       * @return This builder for chaining.
       */
      public Builder addAllPrePasswords(
          java.lang.Iterable<java.lang.String> values) {
        ensurePrePasswordsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, prePasswords_);
        bitField0_ |= 0x00000010;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string prePasswords = 8;</code>
       * @return This builder for chaining.
       */
      public Builder clearPrePasswords() {
        prePasswords_ =
          com.google.protobuf.LazyStringArrayList.emptyList();
        bitField0_ = (bitField0_ & ~0x00000010);;
        onChanged();
        return this;
      }
      /**
       * <code>repeated string prePasswords = 8;</code>
       * @param value The bytes of the prePasswords to add.
       * @return This builder for chaining.
       */
      public Builder addPrePasswordsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) { throw new NullPointerException(); }
        checkByteStringIsUtf8(value);
        ensurePrePasswordsIsMutable();
        prePasswords_.add(value);
        bitField0_ |= 0x00000010;
        onChanged();
        return this;
      }

      // @@protoc_insertion_point(builder_scope:BaseUser)
    }

    // @@protoc_insertion_point(class_scope:BaseUser)
    private static final com.zkart.model.BaseUserProto.BaseUser DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.zkart.model.BaseUserProto.BaseUser();
    }

    public static com.zkart.model.BaseUserProto.BaseUser getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<BaseUser>
        PARSER = new com.google.protobuf.AbstractParser<BaseUser>() {
      @java.lang.Override
      public BaseUser parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        Builder builder = newBuilder();
        try {
          builder.mergeFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          throw e.setUnfinishedMessage(builder.buildPartial());
        } catch (com.google.protobuf.UninitializedMessageException e) {
          throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
        } catch (java.io.IOException e) {
          throw new com.google.protobuf.InvalidProtocolBufferException(e)
              .setUnfinishedMessage(builder.buildPartial());
        }
        return builder.buildPartial();
      }
    };

    public static com.google.protobuf.Parser<BaseUser> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<BaseUser> getParserForType() {
      return PARSER;
    }

    @java.lang.Override
    public com.zkart.model.BaseUserProto.BaseUser getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_BaseUser_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_BaseUser_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n&com/zkart/dbFiles/proto/baseUser.proto" +
      "\"_\n\010BaseUser\022\n\n\002id\030\001 \001(\005\022\r\n\005email\030\002 \001(\t\022" +
      "\020\n\010fullname\030\004 \001(\t\022\020\n\010password\030\005 \001(\t\022\024\n\014p" +
      "rePasswords\030\010 \003(\tB \n\017com.zkart.modelB\rBa" +
      "seUserProtob\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_BaseUser_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_BaseUser_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessage.FieldAccessorTable(
        internal_static_BaseUser_descriptor,
        new java.lang.String[] { "Id", "Email", "Fullname", "Password", "PrePasswords", });
    descriptor.resolveAllFeaturesImmutable();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
